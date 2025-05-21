package ru.snake.serde.serializer.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class CharacterSerailizer extends Serialiser<Character> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final Character object)
			throws IOException {
		stream.writeChar(object);
	}

	@Override
	public Character deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		return stream.readChar();
	}

	@Override
	public String toString() {
		return "CharacterSerailizer []";
	}

}
