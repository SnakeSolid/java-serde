package ru.snake.serde.serializer.object;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import ru.snake.serde.serializer.SerdeConstructor;
import ru.snake.serde.serializer.SerdeContext;
import ru.snake.serde.serializer.SerdeProperty;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class ClassSerializer<T> extends Serialiser<T> {

	private final SerdeConstructor<T> constructor;

	private final List<SerdeProperty> properties;

	public ClassSerializer(SerdeConstructor<T> constructor, List<SerdeProperty> properties) {
		this.constructor = constructor;
		this.properties = properties;
	}

	@Override
	public void serialize(final SerdeContext context, DataOutputStream stream, T object)
			throws IOException, SerdeException {
		for (SerdeProperty property : properties) {
			context.serialize(stream, property.get(object));
		}
	}

	@Override
	public T deserialize(final SerdeContext context, DataInputStream stream) throws IOException, SerdeException {
		T object = constructor.create();

		for (SerdeProperty property : properties) {
			property.set(object, context.deserialize(stream));
		}

		return object;
	}

	@Override
	public String toString() {
		return "ClassSerializer [constructor=" + constructor + ", properties=" + properties + "]";
	}

}
