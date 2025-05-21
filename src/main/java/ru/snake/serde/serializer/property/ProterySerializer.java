package ru.snake.serde.serializer.property;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.SerdeProperty;
import ru.snake.serde.serializer.exception.SerdeException;

public interface ProterySerializer {

	public void serialize(final SerializeContext context, DataOutput stream, Object object)
			throws IOException, SerdeException;

	public void deserialize(final DeserializeContext context, DataInput stream, Object object)
			throws IOException, SerdeException;

	public static ProterySerializer create(final SerdeProperty property) {
		if (property.getPropertyType().isPrimitive()) {
			return new PrimitivePropertySerializer(property);
		} else {
			return new ObjectPropertySerializer(property);
		}
	}

}
