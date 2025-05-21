package ru.snake.serde.stream;

import java.io.DataOutput;
import java.io.OutputStream;

@FunctionalInterface
public interface DataOutputBuilder {

	public DataOutput create(final OutputStream inputStream);

}
