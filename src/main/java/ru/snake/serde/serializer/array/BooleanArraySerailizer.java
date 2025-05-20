package ru.snake.serde.serializer.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class BooleanArraySerailizer extends Serialiser<boolean[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final boolean[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeBoolean(object[index]);
		}
	}

	@Override
	public boolean[] deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		int length = stream.readInt();
		boolean[] result = new boolean[length];

		for (int index = 0; index < length; index += 1) {
			result[index] = stream.readBoolean();
		}

		return result;
	}

	@Override
	public String toString() {
		return "BooleanArraySerailizer []";
	}

}
