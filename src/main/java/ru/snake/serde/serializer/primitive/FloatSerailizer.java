package ru.snake.serde.serializer.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class FloatSerailizer extends Serialiser<Float> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final Float object)
			throws IOException {
		stream.writeFloat(object);
	}

	@Override
	public Float deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		return stream.readFloat();
	}

	@Override
	public String toString() {
		return "FloatSerailizer []";
	}

}
