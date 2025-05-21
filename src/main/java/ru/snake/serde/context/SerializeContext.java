package ru.snake.serde.context;

import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.serializer.exception.SerdeException;

public interface SerializeContext {

	public <T> int addObject(final T object);

	public <T> void serializePrimitive(final DataOutput stream, final T object) throws IOException, SerdeException;

	public <T> void serialize(final DataOutput stream, final T object) throws IOException, SerdeException;

	public <T> void serializeType(final DataOutput stream, final Class<T> clazz) throws IOException, SerdeException;

}
