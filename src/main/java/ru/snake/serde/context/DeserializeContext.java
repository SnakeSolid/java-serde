package ru.snake.serde.context;

import java.io.DataInput;
import java.io.IOException;

import ru.snake.serde.serializer.exception.SerdeException;

public interface DeserializeContext {

	public <T> int addObject(final T object);

	public <T> T deserializePrimitive(DataInput stream, Class<T> clazz) throws IOException, SerdeException;

	public <T> T deserialize(DataInput stream) throws IOException, SerdeException;

	public <T> Class<T> deserializeType(DataInput stream) throws IOException, SerdeException;

}
