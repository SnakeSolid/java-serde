package ru.snake.serde.context.flat;

import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.context.SerializerRegistry;
import ru.snake.serde.context.TypeRegistry;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class FlatSerializeContext implements SerializeContext {

	private final TypeRegistry typeRegistry;

	private final SerializerRegistry serializerRegistry;

	public FlatSerializeContext(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry) {
		this.typeRegistry = typeRegistry;
		this.serializerRegistry = serializerRegistry;
	}

	@Override
	public <T> int addObject(T object) {
		return 0;
	}

	@Override
	public <T> void serializePrimitive(final DataOutput stream, final T object) throws IOException, SerdeException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) object.getClass();
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);

		serializer.serialize(this, stream, object);
	}

	@Override
	public <T> void serialize(final DataOutput stream, final T object) throws IOException, SerdeException {
		if (object == null) {
			int id = typeRegistry.getNullId();
			stream.writeInt(id);

			return;
		}

		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) object.getClass();
		int id = typeRegistry.getId(clazz);
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);

		stream.writeInt(id);
		serializer.serialize(this, stream, object);
	}

	@Override
	public <T> void serializeType(final DataOutput stream, final Class<T> clazz) throws IOException, SerdeException {
		int id = typeRegistry.getId(clazz);

		stream.writeInt(id);
	}

	@Override
	public String toString() {
		return "FlatSerializeContext [typeRegistry=" + typeRegistry + ", serializerRegistry=" + serializerRegistry
				+ "]";
	}

}
