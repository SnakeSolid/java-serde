package ru.snake.serde.serializer.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class ShortSerailizer extends Serialiser<Short> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final Short object)
			throws IOException {
		stream.writeShort(object);
	}

	@Override
	public Short deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		return stream.readShort();
	}

	@Override
	public String toString() {
		return "ShortSerailizer []";
	}

}
