package ru.snake.serde.stream;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class CompactInputStream implements DataInput, AutoCloseable {

	private final InputStream inner;

	private final byte[] doubleBuffer;

	public CompactInputStream(final InputStream inner) {
		this.inner = inner;
		this.doubleBuffer = new byte[8];
	}

	@Override
	public void readFully(byte[] buffer) throws IOException {
		inner.read(buffer, 0, buffer.length);
	}

	@Override
	public void readFully(byte[] buffer, int offset, int length) throws IOException {
		inner.read(buffer, offset, length);
	}

	@Override
	public int skipBytes(int n) throws IOException {
		int total = 0;
		int cur = 0;

		while ((total < n) && ((cur = (int) inner.skip(n - total)) > 0)) {
			total += cur;
		}

		return total;
	}

	@Override
	public boolean readBoolean() throws IOException {
		return readUnsignedByte() != 0;
	}

	@Override
	public byte readByte() throws IOException {
		return (byte) readUnsignedByte();
	}

	@Override
	public int readUnsignedByte() throws IOException {
		int ch = inner.read();

		if (ch < 0) {
			throw new EOFException();
		}

		return ch;
	}

	@Override
	public short readShort() throws IOException {
		int result = 0;
		int shift = 0;
		int b;

		do {
			b = inner.read();

			if (b == -1) {
				throw new EOFException();
			}

			if (shift >= 21) {
				throw new IOException("Malformed short");
			}

			result |= (b & 0x7F) << shift;
			shift += 7;
		} while ((b & 0x80) != 0);

		return (short) ((result >>> 1) ^ -(result & 1));
	}

	@Override
	public int readUnsignedShort() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public char readChar() throws IOException {
		int result = 0;
		int shift = 0;
		int b;

		do {
			b = inner.read();

			if (b == -1) {
				throw new EOFException();
			}

			if (shift >= 21) {
				throw new IOException("Malformed char");
			}

			result |= (b & 0x7F) << shift;
			shift += 7;
		} while ((b & 0x80) != 0);

		if (result > 0xFFFF) {
			throw new IOException("Char overflow");
		}

		return (char) result;
	}

	@Override
	public int readInt() throws IOException {
		int result = 0;
		int shift = 0;
		int b;

		do {
			b = inner.read();

			if (b == -1) {
				throw new EOFException();
			}

			if (shift >= 35) {
				throw new IOException("Malformed int");
			}

			result |= (b & 0x7F) << shift;
			shift += 7;
		} while ((b & 0x80) != 0);

		return (result >>> 1) ^ -(result & 1);
	}

	@Override
	public long readLong() throws IOException {
		long result = 0;
		int shift = 0;
		int b;

		do {
			b = inner.read();

			if (b == -1) {
				throw new EOFException();
			}

			if (shift >= 64) {
				throw new IOException("Malformed stream");
			}

			result |= (long) (b & 0x7F) << shift;
			shift += 7;
		} while ((b & 0x80) != 0);

		return (result >>> 1) ^ -(result & 1);
	}

	@Override
	public float readFloat() throws IOException {
		int ch1 = inner.read();
		int ch2 = inner.read();
		int ch3 = inner.read();
		int ch4 = inner.read();

		if ((ch1 | ch2 | ch3 | ch4) < 0) {
			throw new EOFException();
		}

		int value = ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));

		return Float.intBitsToFloat(value);
	}

	@Override
	public double readDouble() throws IOException {
		readFully(doubleBuffer, 0, 8);

		long value = (((long) doubleBuffer[0] << 56) + ((long) (doubleBuffer[1] & 255) << 48)
				+ ((long) (doubleBuffer[2] & 255) << 40) + ((long) (doubleBuffer[3] & 255) << 32)
				+ ((long) (doubleBuffer[4] & 255) << 24) + ((doubleBuffer[5] & 255) << 16)
				+ ((doubleBuffer[6] & 255) << 8) + ((doubleBuffer[7] & 255) << 0));

		return Double.longBitsToDouble(value);
	}

	@Override
	public String readLine() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String readUTF() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() throws Exception {
		inner.close();
	}

}
