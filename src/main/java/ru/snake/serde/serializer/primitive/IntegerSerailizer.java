package ru.snake.serde.serializer.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class IntegerSerailizer extends Serialiser<Integer> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final Integer object)
			throws IOException {
		stream.writeInt(object);
	}

	@Override
	public Integer deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		return stream.readInt();
	}

	@Override
	public String toString() {
		return "IntegerSerailizer []";
	}

}
