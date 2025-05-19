package ru.snake.serde;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import ru.snake.serde.serializer.Serialiser;

public class DataSerializer {

	private final Map<Class<?>, Serialiser<?>> serializers;

	private final Map<Object, Integer> objects;

	public DataSerializer(final Map<Class<?>, Serialiser<?>> serializers) {
		this.serializers = new HashMap<>();
		this.objects = new IdentityHashMap<>();
	}

	public <T> void serialize(final DataOutputStream stream, final T object) throws IOException {
		writeAny(stream, object);
	}

	private <T> void writeAny(final DataOutputStream stream, final T object) throws IOException {
		if (object == null) {
			stream.writeByte(0);
		}

		Class<?> type = object.getClass();

		if (type.isPrimitive()) {
			writePrimitive(stream, type, object);
		} else {
			throw new RuntimeException("Unsupported class");
		}
	}

	private void writePrimitive(DataOutputStream stream, Class<?> type, Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "DataSerializer [serializers=" + serializers + ", objects=" + objects + "]";
	}

}
