package ru.snake.serde.context;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class SerdeContext {

	private final TypeRegistry typeRegistry;

	private final SerializerRegistry serializerRegistry;

	public SerdeContext(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry) {
		this.typeRegistry = typeRegistry;
		this.serializerRegistry = serializerRegistry;
	}

	public <T> void serializePrimitive(final DataOutput stream, final T object) throws IOException, SerdeException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) object.getClass();
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);

		serializer.serialize(this, stream, object);
	}

	public <T> T deserializePrimitive(DataInput stream, Class<T> clazz) throws IOException, SerdeException {
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);
		T result = serializer.deserialize(this, stream);

		return result;
	}

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

	public <T> T deserialize(DataInput stream) throws IOException, SerdeException {
		int id = stream.readInt();

		if (id == typeRegistry.getNullId()) {
			return null;
		}

		Class<T> clazz = typeRegistry.getClass(id);
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);
		T result = serializer.deserialize(this, stream);

		return result;
	}

	public <T> void serializeType(final DataOutput stream, final Class<T> clazz) throws IOException, SerdeException {
		int id = typeRegistry.getId(clazz);

		stream.writeInt(id);
	}

	public <T> Class<T> deserializeType(DataInput stream) throws IOException, SerdeException {
		int id = stream.readInt();
		Class<T> clazz = typeRegistry.getClass(id);

		return clazz;
	}

	@Override
	public String toString() {
		return "SerdeContext [typeRegistry=" + typeRegistry + ", serializerRegistry=" + serializerRegistry + "]";
	}

}
