package ru.snake.serde.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CompactStreamTest {

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	public void testBoolean(final boolean value) throws Throwable {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (CompactOutputStream stream = new CompactOutputStream(output)) {
			stream.writeBoolean(value);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

		try (CompactInputStream stream = new CompactInputStream(input)) {
			boolean result = stream.readBoolean();

			Assertions.assertEquals(value, result);
		}
	}

	@ParameterizedTest
	@ValueSource(
		shorts = { (short) 0, (short) 1, (short) -1, Short.MAX_VALUE, Short.MIN_VALUE, (short) 255, (short) -255 }
	)
	public void testShort(final short value) throws Throwable {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (CompactOutputStream stream = new CompactOutputStream(output)) {
			stream.writeShort(value);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

		try (CompactInputStream stream = new CompactInputStream(input)) {
			short result = stream.readShort();

			Assertions.assertEquals(value, result);
		}
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 1, -1, 127, -128, 255, -255, Integer.MAX_VALUE, Integer.MIN_VALUE, 1 << 30, -(1 << 30) })
	public void testInteger(final int value) throws Throwable {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (CompactOutputStream stream = new CompactOutputStream(output)) {
			stream.writeInt(value);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

		try (CompactInputStream stream = new CompactInputStream(input)) {
			int result = stream.readInt();

			Assertions.assertEquals(value, result);
		}
	}

	@ParameterizedTest
	@ValueSource(
		longs = { 0L, 1L, -1L, 127L, -128L, 255L, -255L, 123456789L, -123456789L, Long.MAX_VALUE, Long.MIN_VALUE,
				(1L << 35) - 1, -(1L << 35) }
	)
	public void testLong(final long value) throws Throwable {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (CompactOutputStream stream = new CompactOutputStream(output)) {
			stream.writeLong(value);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

		try (CompactInputStream stream = new CompactInputStream(input)) {
			long result = stream.readLong();

			Assertions.assertEquals(value, result);
		}
	}

	@ParameterizedTest
	@ValueSource(chars = { (char) 0, (char) 1, (char) 255, Character.MAX_VALUE, 'A', 'Я', '\uD83D' })
	public void testChar(final char value) throws Throwable {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (CompactOutputStream stream = new CompactOutputStream(output)) {
			stream.writeChar(value);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

		try (CompactInputStream stream = new CompactInputStream(input)) {
			char result = stream.readChar();

			Assertions.assertEquals(value, result);
		}
	}

	@ParameterizedTest
	@ValueSource(bytes = { (byte) 0, (byte) 1, (byte) -1, Byte.MAX_VALUE, Byte.MIN_VALUE, (byte) 127, (byte) -128 })
	public void testByte(final byte value) throws Throwable {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (CompactOutputStream stream = new CompactOutputStream(output)) {
			stream.writeByte(value);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

		try (CompactInputStream stream = new CompactInputStream(input)) {
			byte result = stream.readByte();

			Assertions.assertEquals(value, result);
		}
	}

	@ParameterizedTest
	@ValueSource(
		floats = { 0.0f, 1.0f, -1.0f, Float.MAX_VALUE, Float.MIN_VALUE, Float.NaN, Float.POSITIVE_INFINITY,
				Float.NEGATIVE_INFINITY, 3.14159265f }
	)
	public void testFloat(final float value) throws Throwable {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (CompactOutputStream stream = new CompactOutputStream(output)) {
			stream.writeFloat(value);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

		try (CompactInputStream stream = new CompactInputStream(input)) {
			float result = stream.readFloat();

			Assertions.assertEquals(value, result);
		}
	}

	@ParameterizedTest
	@ValueSource(
		doubles = { 0.0, 1.0, -1.0, Double.MAX_VALUE, Double.MIN_VALUE, Double.NaN, Double.POSITIVE_INFINITY,
				Double.NEGATIVE_INFINITY, Math.PI }
	)
	public void testDouble(final double value) throws Throwable {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try (CompactOutputStream stream = new CompactOutputStream(output)) {
			stream.writeDouble(value);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());

		try (CompactInputStream stream = new CompactInputStream(input)) {
			double result = stream.readDouble();

			Assertions.assertEquals(value, result);
		}
	}

}
