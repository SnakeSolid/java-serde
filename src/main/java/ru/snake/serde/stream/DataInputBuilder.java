package ru.snake.serde.stream;

import java.io.DataInput;
import java.io.InputStream;

@FunctionalInterface
public interface DataInputBuilder {

	public DataInput create(final InputStream inputStream);

}
