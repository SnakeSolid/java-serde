package ru.snake.serde.serializer.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.serializer.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class LongArraySerailizer extends Serialiser<long[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final long[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeLong(object[index]);
		}
	}

	@Override
	public long[] deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		int length = stream.readInt();
		long[] result = new long[length];

		for (int index = 0; index < length; index += 1) {
			result[index] = stream.readLong();
		}

		return result;
	}

	@Override
	public String toString() {
		return "LongArraySerailizer []";
	}

}
