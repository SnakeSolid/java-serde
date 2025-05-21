package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.SerdeConstructor;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;
import ru.snake.serde.serializer.property.ProterySerializer;

public class ClassSerializer<T> extends Serialiser<T> {

	private final SerdeConstructor<T> constructor;

	private final List<ProterySerializer> properties;

	public ClassSerializer(SerdeConstructor<T> constructor, List<ProterySerializer> properties) {
		this.constructor = constructor;
		this.properties = properties;
	}

	@Override
	public void serialize(final SerdeContext context, DataOutput stream, T object) throws IOException, SerdeException {
		for (ProterySerializer property : properties) {
			property.serialize(context, stream, object);
		}
	}

	@Override
	public T deserialize(final SerdeContext context, DataInput stream) throws IOException, SerdeException {
		T object = constructor.create();

		for (ProterySerializer property : properties) {
			property.deserialize(context, stream, object);
		}

		return object;
	}

	@Override
	public String toString() {
		return "ClassSerializer [constructor=" + constructor + ", properties=" + properties + "]";
	}

}
