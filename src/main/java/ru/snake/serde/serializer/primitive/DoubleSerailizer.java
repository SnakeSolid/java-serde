package ru.snake.serde.serializer.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class DoubleSerailizer extends Serialiser<Double> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final Double object)
			throws IOException {
		stream.writeDouble(object);
	}

	@Override
	public Double deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		return stream.readDouble();
	}

	@Override
	public String toString() {
		return "DoubleSerailizer []";
	}

}
