package ru.snake.serde.serializer.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class DoubleSerailizer extends Serialiser<Double> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final Double object)
			throws IOException {
		stream.writeDouble(object);
	}

	@Override
	public Double deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		return stream.readDouble();
	}

	@Override
	public String toString() {
		return "DoubleSerailizer []";
	}

}
