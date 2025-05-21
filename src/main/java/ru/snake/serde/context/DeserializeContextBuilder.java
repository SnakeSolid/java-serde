package ru.snake.serde.context;

@FunctionalInterface
public interface DeserializeContextBuilder {

	public DeserializeContext create(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry);

}
