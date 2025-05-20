package ru.snake.serde.serializer.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;

public class BooleanSerailizer extends Serialiser<Boolean> {

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final Boolean object)
			throws IOException {
		stream.writeBoolean(object);
	}

	@Override
	public Boolean deserialize(final SerdeContext context, final DataInputStream stream) throws IOException {
		return stream.readBoolean();
	}

	@Override
	public String toString() {
		return "BooleanSerailizer []";
	}

}
