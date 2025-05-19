package ru.snake.serde;

import java.util.Map;

import ru.snake.serde.serializer.Serialiser;

public class DataDeserializer {

	private final Map<Class<?>, Serialiser<?>> serializers;

	public DataDeserializer(final Map<Class<?>, Serialiser<?>> serializers) {
		this.serializers = serializers;
	}

	@Override
	public String toString() {
		return "DataDeserializer [serializers=" + serializers + "]";
	}

}
