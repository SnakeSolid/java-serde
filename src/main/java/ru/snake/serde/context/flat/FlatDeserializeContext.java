package ru.snake.serde.context.flat;

import java.io.DataInput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializerRegistry;
import ru.snake.serde.context.TypeRegistry;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class FlatDeserializeContext implements DeserializeContext {

	private final TypeRegistry typeRegistry;

	private final SerializerRegistry serializerRegistry;

	public FlatDeserializeContext(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry) {
		this.typeRegistry = typeRegistry;
		this.serializerRegistry = serializerRegistry;
	}

	@Override
	public <T> int addObject(T object) {
		return 0;
	}

	@Override
	public <T> T deserializePrimitive(DataInput stream, Class<T> clazz) throws IOException, SerdeException {
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);
		T result = serializer.deserialize(this, stream);

		return result;
	}

	@Override
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

	@Override
	public <T> Class<T> deserializeType(DataInput stream) throws IOException, SerdeException {
		int id = stream.readInt();
		Class<T> clazz = typeRegistry.getClass(id);

		return clazz;
	}

	@Override
	public String toString() {
		return "FlatDeserializeContext [typeRegistry=" + typeRegistry + ", serializerRegistry=" + serializerRegistry
				+ "]";
	}

}
