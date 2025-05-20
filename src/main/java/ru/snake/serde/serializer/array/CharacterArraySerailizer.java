package ru.snake.serde.serializer.array;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class CharacterArraySerailizer extends Serialiser<char[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final char[] object)
			throws IOException {
		stream.writeInt(object.length);

		for (int index = 0; index < object.length; index += 1) {
			stream.writeChar(object[index]);
		}
	}

	@Override
	public char[] deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		int length = stream.readInt();
		char[] result = new char[length];

		for (int index = 0; index < length; index += 1) {
			result[index] = stream.readChar();
		}

		return result;
	}

	@Override
	public String toString() {
		return "CharacterArraySerailizer []";
	}

}
