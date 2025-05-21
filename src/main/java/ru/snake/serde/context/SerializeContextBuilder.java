package ru.snake.serde.context;

@FunctionalInterface
public interface SerializeContextBuilder {

	public SerializeContext create(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry);

}
