package ru.snake.serde.serializer.array;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class DoubleArraySerailizer extends Serialiser<double[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutput stream, final double[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeDouble(object[index]);
		}
	}

	@Override
	public double[] deserialize(final SerdeContext context, final DataInput stream) throws IOException {
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
