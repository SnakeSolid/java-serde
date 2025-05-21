package ru.snake.serde.context;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class ReferenceSerdeContext implements SerdeContext {

	private static final int START_REFERENCE_ID = 1;

	private final TypeRegistry typeRegistry;

	private final SerializerRegistry serializerRegistry;

	private final Map<Object, Integer> objectReferences;

	private final Map<Integer, Object> referenceObjects;

	private int nextReference;

	public ReferenceSerdeContext(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry) {
		this.typeRegistry = typeRegistry;
		this.serializerRegistry = serializerRegistry;
		this.objectReferences = new IdentityHashMap<>();
		this.referenceObjects = new HashMap<>();
		this.nextReference = START_REFERENCE_ID;
	}

	@Override
	public <T> int addObject(final T object) {
		Integer nextId = objectReferences.get(object);

		if (nextId == null) {
			nextId = nextReference;
			objectReferences.put(object, nextId);
			referenceObjects.put(nextId, object);
			nextReference += 1;
		}

		return nextId;
	}

	@Override
	public <T> void serializePrimitive(final DataOutput stream, final T object) throws IOException, SerdeException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) object.getClass();
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);

		serializer.serialize(this, stream, object);
	}

	@Override
	public <T> T deserializePrimitive(DataInput stream, Class<T> clazz) throws IOException, SerdeException {
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);
		T result = serializer.deserialize(this, stream);

		return result;
	}

	@Override
	public <T> void serialize(final DataOutput stream, final T object) throws IOException, SerdeException {
		if (object == null) {
			stream.writeInt(typeRegistry.getNullId());

			return;
		}

		Integer referenceId = objectReferences.get(object);

		if (referenceId != null) {
			stream.writeInt(typeRegistry.getReferenceId());
			stream.writeInt(referenceId);

			return;
		}

		addObject(object);

		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) object.getClass();
		int id = typeRegistry.getId(clazz);
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);

		stream.writeInt(id);
		serializer.serialize(this, stream, object);
	}

	@Override
	public <T> T deserialize(DataInput stream) throws IOException, SerdeException {
		int id = stream.readInt();

		if (id == typeRegistry.getNullId()) {
			return null;
		} else if (id == typeRegistry.getReferenceId()) {
			int referenceId = stream.readInt();
			@SuppressWarnings("unchecked")
			T result = (T) referenceObjects.get(referenceId);

			return result;
		}

		Class<T> clazz = typeRegistry.getClass(id);
		Serialiser<T> serializer = serializerRegistry.getSerializer(clazz);
		T result = serializer.deserialize(this, stream);

		addObject(result);

		return result;
	}

	@Override
	public <T> void serializeType(final DataOutput stream, final Class<T> clazz) throws IOException, SerdeException {
		int id = typeRegistry.getId(clazz);

		stream.writeInt(id);
	}

	@Override
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
