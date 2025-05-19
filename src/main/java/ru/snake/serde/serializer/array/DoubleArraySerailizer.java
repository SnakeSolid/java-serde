package ru.snake.serde.serializer.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.serializer.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class DoubleArraySerailizer extends Serialiser<double[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final double[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeDouble(object[index]);
		}
	}

	@Override
	public double[] deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		int length = stream.readInt();
		double[] result = new double[length];

		for (int index = 0; index < length; index += 1) {
			result[index] = stream.readDouble();
		}

		return result;
	}

	@Override
	public String toString() {
		return "DoubleArraySerailizer []";
	}

}
