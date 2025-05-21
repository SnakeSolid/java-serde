package ru.snake.serde.context;

@FunctionalInterface
public interface ContextBuilder {

	public SerdeContext create(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry);

}
