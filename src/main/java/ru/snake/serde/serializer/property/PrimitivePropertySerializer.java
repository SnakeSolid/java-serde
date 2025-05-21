package ru.snake.serde.serializer.property;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.SerdeProperty;
import ru.snake.serde.serializer.exception.SerdeException;

public class PrimitivePropertySerializer implements ProterySerializer {

	private final SerdeProperty property;

	public PrimitivePropertySerializer(final SerdeProperty property) {
		this.property = property;
	}

	@Override
	public void serialize(SerializeContext context, DataOutput stream, Object object)
			throws IOException, SerdeException {
		context.serializePrimitive(stream, property.get(object));
	}

	@Override
	public void deserialize(DeserializeContext context, DataInput stream, Object object)
			throws IOException, SerdeException {
		property.set(object, context.deserializePrimitive(stream, property.getPropertyType()));
	}

	@Override
	public String toString() {
		return "PrimitivePropertySerializer [property=" + property + "]";
	}

}
