package ru.snake.serde.serializer.array;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class IntegerArraySerailizer extends Serialiser<int[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutput stream, final int[] object) throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeInt(object[index]);
		}
	}

	@Override
	public int[] deserialize(final SerdeContext context, final DataInput stream) throws IOException {
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
