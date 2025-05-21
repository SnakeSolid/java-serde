package ru.snake.serde.serializer.array;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class ByteArraySerailizer extends Serialiser<byte[]> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final byte[] object)
			throws IOException {
		stream.writeInt(object.length);
		stream.write(object);
	}

	@Override
	public byte[] deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		int length = stream.readInt();
		byte[] result = new byte[length];
		stream.readFully(result);

		return result;
	}

	@Override
	public String toString() {
		return "ByteArraySerailizer []";
	}

}
