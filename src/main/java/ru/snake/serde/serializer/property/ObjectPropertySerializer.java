package ru.snake.serde.serializer.property;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.SerdeProperty;
import ru.snake.serde.serializer.exception.SerdeException;

public class ObjectPropertySerializer implements ProterySerializer {

	private final SerdeProperty property;

	public ObjectPropertySerializer(final SerdeProperty property) {
		this.property = property;
	}

	@Override
	public void serialize(SerdeContext context, DataOutputStream stream, Object object)
			throws IOException, SerdeException {
		context.serialize(stream, property.get(object));
	}

	@Override
	public void deserialize(SerdeContext context, DataInputStream stream, Object object)
			throws IOException, SerdeException {
		property.set(object, context.deserialize(stream));
	}

	@Override
	public String toString() {
		return "ObjectPropertySerializer [property=" + property + "]";
	}

}
