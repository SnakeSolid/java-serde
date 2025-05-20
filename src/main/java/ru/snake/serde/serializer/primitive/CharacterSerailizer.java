package ru.snake.serde.serializer.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class CharacterSerailizer extends Serialiser<Character> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final Character object)
			throws IOException {
		stream.writeChar(object);
	}

	@Override
	public Character deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		return stream.readChar();
	}

	@Override
	public String toString() {
		return "CharacterSerailizer []";
	}

}
