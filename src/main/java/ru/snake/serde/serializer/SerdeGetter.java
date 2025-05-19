package ru.snake.serde.serializer;

@FunctionalInterface
public interface SerdeGetter<T> {

	public T get(final T object);

}
