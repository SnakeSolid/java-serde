package ru.snake.serde.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.Serde;
import ru.snake.serde.serializer.exception.SerdeException;

public class SerdeContext {

	private final Serde serde;

	public SerdeContext(final Serde serde) {
		this.serde = serde;
	}

	public void serialize(final DataOutputStream stream, final Object object) throws IOException, SerdeException {
		serde.serialize(stream, object);
	}

	public <T> T deserialize(DataInputStream stream) throws IOException, SerdeException {
		return serde.deserialize(stream);
	}

	@Override
	public String toString() {
		return "SerdeContext [serde=" + serde + "]";
	}

}
