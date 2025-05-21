package ru.snake.serde.serializer.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class BooleanSerailizer extends Serialiser<Boolean> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final Boolean object)
			throws IOException {
		stream.writeBoolean(object);
	}

	@Override
	public Boolean deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		return stream.readBoolean();
	}

	@Override
	public String toString() {
		return "BooleanSerailizer []";
	}

}
