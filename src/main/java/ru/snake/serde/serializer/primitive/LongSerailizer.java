package ru.snake.serde.serializer.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class LongSerailizer extends Serialiser<Long> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final Long object)
			throws IOException {
		stream.writeLong(object);
	}

	@Override
	public Long deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		return stream.readLong();
	}

	@Override
	public String toString() {
		return "LongSerailizer []";
	}

}
