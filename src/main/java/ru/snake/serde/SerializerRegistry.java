package ru.snake.serde;

import java.util.HashMap;
import java.util.Map;

import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeNoSerializerException;
import ru.snake.serde.serializer.exception.SerdeReflectiveException;

public class SerializerRegistry {

	private final Map<Class<?>, Serialiser<?>> serializers;

	private SerializerRegistry() {
		this.serializers = new HashMap<>();
	}

	public <T> Serialiser<T> getSerializer(Class<T> clazz) throws SerdeNoSerializerException {
		@SuppressWarnings("unchecked")
		Serialiser<T> serialiser = (Serialiser<T>) serializers.get(clazz);

		if (serialiser == null) {
			throw new SerdeNoSerializerException(
				String.format("Serializer for class %s was not registered.", clazz.getCanonicalName())
			);
		}

		return serialiser;
	}

	public <T> void register(final Class<T> clazz) throws SerdeReflectiveException {
		Serialiser<T> serialiser = Serialiser.builder(clazz).build();

		serializers.put(clazz, serialiser);
	}

	public <T> void register(final Class<T> clazz, final Serialiser<T> serialiser) {
		serializers.put(clazz, serialiser);
	}

	@Override
	public String toString() {
		return "SerializerRegistry [serializers=" + serializers + "]";
	}

	public static SerializerRegistry create() {
		SerializerRegistry registry = new SerializerRegistry();

		return registry;
	}

}
