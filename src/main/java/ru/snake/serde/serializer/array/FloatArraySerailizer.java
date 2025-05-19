package ru.snake.serde.serializer.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.serializer.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class FloatArraySerailizer extends Serialiser<float[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final float[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeFloat(object[index]);
		}
	}

	@Override
	public float[] deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
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
