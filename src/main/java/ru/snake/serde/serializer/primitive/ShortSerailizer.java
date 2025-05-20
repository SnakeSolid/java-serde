package ru.snake.serde.serializer.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class ShortSerailizer extends Serialiser<Short> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final Short object)
			throws IOException {
		stream.writeShort(object);
	}

	@Override
	public Short deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		return stream.readShort();
	}

	@Override
	public String toString() {
		return "ShortSerailizer []";
	}

}
