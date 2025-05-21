package ru.snake.serde.context.reference;

import java.io.DataOutput;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;

import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.context.SerializerRegistry;
import ru.snake.serde.context.TypeRegistry;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class ReferenceSerializeContext implements SerializeContext {

	private static final int START_REFERENCE_ID = 1;

	private final TypeRegistry typeRegistry;

	private final SerializerRegistry serializerRegistry;

	private final Map<Object, Integer> objectReferences;

	private int nextReference;

	public ReferenceSerializeContext(final TypeRegistry typeRegistry, final SerializerRegistry serializerRegistry) {
		this.typeRegistry = typeRegistry;
		this.serializerRegistry = serializerRegistry;
		this.objectReferences = new IdentityHashMap<>();
		this.nextReference = START_REFERENCE_ID;
	}

	@Override
	public <T> int addObject(final T object) {
		Integer nextId = objectReferences.get(object);

		if (nextId == null) {
			nextId = nextReference;
			objectReferences.put(object, nextId);
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
	public <T> void serializeType(final DataOutput stream, final Class<T> clazz) throws IOException, SerdeException {
		int id = typeRegistry.getId(clazz);

		stream.writeInt(id);
	}

	@Override
	public String toString() {
		return "ReferenceSerializeContext [typeRegistry=" + typeRegistry + ", serializerRegistry=" + serializerRegistry
				+ ", objectReferences=" + objectReferences + ", nextReference=" + nextReference + "]";
	}

}
