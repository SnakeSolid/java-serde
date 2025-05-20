package ru.snake.serde.serializer.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class ByteSerailizer extends Serialiser<Byte> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final Byte object)
			throws IOException {
		stream.writeByte(object);
	}

	@Override
	public Byte deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		return stream.readByte();
	}

	@Override
	public String toString() {
		return "ByteSerailizer []";
	}

}
