package ru.snake.serde.serializer.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class FloatSerailizer extends Serialiser<Float> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final Float object)
			throws IOException {
		stream.writeFloat(object);
	}

	@Override
	public Float deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		return stream.readFloat();
	}

	@Override
	public String toString() {
		return "FloatSerailizer []";
	}

}
