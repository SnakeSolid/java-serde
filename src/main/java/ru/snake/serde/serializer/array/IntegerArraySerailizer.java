package ru.snake.serde.serializer.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class IntegerArraySerailizer extends Serialiser<int[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final int[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeInt(object[index]);
		}
	}

	@Override
	public int[] deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		int length = stream.readInt();
		int[] result = new int[length];

		for (int index = 0; index < length; index += 1) {
			result[index] = stream.readInt();
		}

		return result;
	}

	@Override
	public String toString() {
		return "IntegerArraySerailizer []";
	}

}
