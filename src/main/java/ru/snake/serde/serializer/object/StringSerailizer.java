package ru.snake.serde.serializer.object;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.serializer.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class StringSerailizer extends Serialiser<String> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final String object)
			throws IOException {
		byte[] bytes = object.getBytes();

		stream.writeInt(bytes.length);
		stream.write(bytes);
	}

	@Override
	public String deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		int length = stream.readInt();
		byte[] bytes = stream.readNBytes(length);

		return new String(bytes);
	}

	@Override
	public String toString() {
		return "StringSerailizer []";
	}

}
