package ru.snake.serde.serializer.array;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class FloatArraySerailizer extends Serialiser<float[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutput stream, final float[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeFloat(object[index]);
		}
	}

	@Override
	public float[] deserialize(final SerdeContext context, final DataInput stream) throws IOException {
		int length = stream.readInt();
		float[] result = new float[length];

		for (int index = 0; index < length; index += 1) {
			result[index] = stream.readFloat();
		}

		return result;
	}

	@Override
	public String toString() {
		return "FloatArraySerailizer []";
	}

}
