package ru.snake.serde.serializer.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class ShortArraySerailizer extends Serialiser<short[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final short[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeShort(object[index]);
		}
	}

	@Override
	public short[] deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		int length = stream.readInt();
		short[] result = new short[length];

		for (int index = 0; index < length; index += 1) {
			result[index] = stream.readShort();
		}

		return result;
	}

	@Override
	public String toString() {
		return "ShortArraySerailizer []";
	}

}
