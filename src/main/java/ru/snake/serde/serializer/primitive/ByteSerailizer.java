package ru.snake.serde.serializer.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class ByteSerailizer extends Serialiser<Byte> {

	@Override
	public void serialize(final SerdeContext context, final DataOutput stream, final Byte object) throws IOException {
		stream.writeByte(object);
	}

	@Override
	public Byte deserialize(final SerdeContext context, final DataInput stream) throws IOException {
		return stream.readByte();
	}

	@Override
	public String toString() {
		return "ByteSerailizer []";
	}

}
