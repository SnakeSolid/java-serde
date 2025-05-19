package ru.snake.serde.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.snake.serde.SerializerRegistry;
import ru.snake.serde.TypeRegistry;
import ru.snake.serde.serializer.exception.SerdeException;

public class SerdeContext {

	private final TypeRegistry typeRegistry;

	private final SerializerRegistry serializerRegistry;

	public SerdeContext(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry) {
		this.typeRegistry = typeRegistry;
		this.serializerRegistry = serializerRegistry;
	}

	public <T> void serialize(final DataOutputStream stream, final T object) throws IOException, SerdeException {
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

	public <T> T deserialize(DataInputStream stream) throws IOException, SerdeException {
		int id = stream.readInt();

		if (id == typeRegistry.getNullId()) {
			return null;
		}

		Class<T> clazz = typeRegistry.getClass(id);
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);
		T result = serializer.deserialize(this, stream);

		return result;
	}

	public <T> void serializeType(final DataOutputStream stream, final Class<T> clazz)
			throws IOException, SerdeException {
		int id = typeRegistry.getId(clazz);

		stream.writeInt(id);
	}

	public <T> Class<T> deserializeType(DataInputStream stream) throws IOException, SerdeException {
		int id = stream.readInt();
		Class<T> clazz = typeRegistry.getClass(id);

		return clazz;
	}

	@Override
	public String toString() {
		return "SerdeContext [typeRegistry=" + typeRegistry + ", serializerRegistry=" + serializerRegistry + "]";
	}

}
