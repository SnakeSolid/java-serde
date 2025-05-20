package ru.snake.serde.serializer.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class ByteArraySerailizer extends Serialiser<byte[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final byte[] object)
			throws IOException {
		stream.writeInt(object.length);
		stream.write(object);
	}

	@Override
	public byte[] deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		int length = stream.readInt();
		byte[] result = stream.readNBytes(length);

		return result;
	}

	@Override
	public String toString() {
		return "ByteArraySerailizer []";
	}

}
