package ru.snake.serde.serializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.exception.SerdeException;
import ru.snake.serde.serializer.object.ClassSerialiserBuilder;

public abstract class Serialiser<T> {

	public abstract void serialize(final SerdeContext context, final DataOutput stream, final T object)
			throws IOException, SerdeException;

	public abstract T deserialize(final SerdeContext context, final DataInput stream)
			throws IOException, SerdeException;

	public static <T> ClassSerialiserBuilder<T> builder(final Class<T> clazz) {
		return new ClassSerialiserBuilder<>(clazz);
	}

}
