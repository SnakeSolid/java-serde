package ru.snake.serde.context;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.serializer.exception.SerdeException;

public interface SerdeContext {

	public <T> int addObject(final T object);

	public <T> void serializePrimitive(final DataOutput stream, final T object) throws IOException, SerdeException;

	public <T> T deserializePrimitive(DataInput stream, Class<T> clazz) throws IOException, SerdeException;

	public <T> void serialize(final DataOutput stream, final T object) throws IOException, SerdeException;

	public <T> T deserialize(DataInput stream) throws IOException, SerdeException;

	public <T> void serializeType(final DataOutput stream, final Class<T> clazz) throws IOException, SerdeException;

	public <T> Class<T> deserializeType(DataInput stream) throws IOException, SerdeException;

}
