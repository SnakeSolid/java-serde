package ru.snake.serde.serializer.array;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class ShortArraySerailizer extends Serialiser<short[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutput stream, final short[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeShort(object[index]);
		}
	}

	@Override
	public short[] deserialize(final SerdeContext context, final DataInput stream) throws IOException {
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
