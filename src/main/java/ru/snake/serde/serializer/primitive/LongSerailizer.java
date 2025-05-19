package ru.snake.serde.serializer.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.serializer.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class LongSerailizer extends Serialiser<Long> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final Long object)
			throws IOException {
		stream.writeLong(object);
	}

	@Override
	public Long deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		return stream.readLong();
	}

	@Override
	public String toString() {
		return "LongSerailizer []";
	}

}
