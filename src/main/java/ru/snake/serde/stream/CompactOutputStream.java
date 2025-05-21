package ru.snake.serde.stream;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

public class CompactOutputStream implements DataOutput, AutoCloseable {

	private final OutputStream inner;

	private final byte[] writeBuffer;

	public CompactOutputStream(final OutputStream inner) {
		this.inner = inner;
		this.writeBuffer = new byte[8];
	}

	@Override
	public void write(int buffer) throws IOException {
		inner.write(buffer);
	}

	@Override
	public void write(byte[] buffer) throws IOException {
		inner.write(buffer, 0, buffer.length);
	}

	@Override
	public void write(byte[] buffer, int offset, int lenght) throws IOException {
		inner.write(buffer, offset, lenght);
	}

	@Override
	public void writeBoolean(boolean value) throws IOException {
		inner.write(value ? 1 : 0);
	}

	@Override
	public void writeByte(int value) throws IOException {
		inner.write(value);
	}

	@Override
	public void writeShort(int value) throws IOException {
		int zigZag = (value << 1) ^ (value >> 15);

		while (true) {
			int b = zigZag & 0x7F;
			zigZag >>>= 7;

			if (zigZag != 0) {
				b |= 0x80;
			}

			inner.write(b);

			if (zigZag == 0) {
				break;
			}
		}
	}

	@Override
	public void writeChar(int value) throws IOException {
		int val = value;

		while (true) {
			int b = val & 0x7F;
			val >>>= 7;

			if (val != 0) {
				b |= 0x80;
			}

			inner.write(b);

			if (val == 0) {
				break;
			}
		}
	}

	@Override
	public void writeInt(int value) throws IOException {
		int zigZag = (value << 1) ^ (value >> 31);

		while (true) {
			int b = zigZag & 0x7F;
			zigZag >>>= 7;

			if (zigZag != 0) {
				b |= 0x80;
			}

			inner.write(b);

			if (zigZag == 0) {
				break;
			}
		}
	}

	@Override
	public void writeLong(long value) throws IOException {
		long zigZag = (value << 1) ^ (value >> 63);

		while (true) {
			int b = (int) (zigZag & 0x7F);

			zigZag >>>= 7;

			if (zigZag != 0) {
				b |= 0x80;
			}

			inner.write(b);

			if (zigZag == 0) {
				break;
			}
		}
	}

	@Override
	public void writeFloat(float value) throws IOException {
		int bits = Float.floatToIntBits(value);
		writeBuffer[0] = (byte) (bits >>> 24);
		writeBuffer[1] = (byte) (bits >>> 16);
		writeBuffer[2] = (byte) (bits >>> 8);
		writeBuffer[3] = (byte) (bits >>> 0);
		inner.write(writeBuffer, 0, 4);
	}

	@Override
	public void writeDouble(double value) throws IOException {
		long bits = Double.doubleToLongBits(value);
		writeBuffer[0] = (byte) (bits >>> 56);
		writeBuffer[1] = (byte) (bits >>> 48);
		writeBuffer[2] = (byte) (bits >>> 40);
		writeBuffer[3] = (byte) (bits >>> 32);
		writeBuffer[4] = (byte) (bits >>> 24);
		writeBuffer[5] = (byte) (bits >>> 16);
		writeBuffer[6] = (byte) (bits >>> 8);
		writeBuffer[7] = (byte) (bits >>> 0);
		inner.write(writeBuffer, 0, 8);
	}

	@Override
	public void writeBytes(String value) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeChars(String value) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeUTF(String value) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() throws Exception {
		inner.close();
	}

}
