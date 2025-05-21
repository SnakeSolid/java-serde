package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class StringSerailizer extends Serialiser<String> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final String object)
			throws IOException {
		byte[] bytes = object.getBytes();

		stream.writeInt(bytes.length);
		stream.write(bytes);
	}

	@Override
	public String deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		int length = stream.readInt();
		byte[] buffer = new byte[length];
		stream.readFully(buffer);

		return new String(buffer);
	}

	@Override
	public String toString() {
		return "StringSerailizer []";
	}

}
