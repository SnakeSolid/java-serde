package ru.snake.serde;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.DeserializeContextBuilder;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.context.SerializeContextBuilder;
import ru.snake.serde.context.SerializerRegistry;
import ru.snake.serde.context.TypeRegistry;
import ru.snake.serde.context.flat.FlatDeserializeContext;
import ru.snake.serde.context.flat.FlatSerializeContext;
import ru.snake.serde.context.reference.ReferenceDeserializeContext;
import ru.snake.serde.context.reference.ReferenceSerializeContext;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.array.BooleanArraySerailizer;
import ru.snake.serde.serializer.array.ByteArraySerailizer;
import ru.snake.serde.serializer.array.CharacterArraySerailizer;
import ru.snake.serde.serializer.array.DoubleArraySerailizer;
import ru.snake.serde.serializer.array.FloatArraySerailizer;
import ru.snake.serde.serializer.array.IntegerArraySerailizer;
import ru.snake.serde.serializer.array.LongArraySerailizer;
import ru.snake.serde.serializer.array.ObjectArraySerailizer;
import ru.snake.serde.serializer.array.ShortArraySerailizer;
import ru.snake.serde.serializer.exception.SerdeException;
import ru.snake.serde.serializer.object.CollectionSerailizer;
import ru.snake.serde.serializer.object.EnumerationSerailizer;
import ru.snake.serde.serializer.object.MapSerailizer;
import ru.snake.serde.serializer.object.StringSerailizer;
import ru.snake.serde.serializer.primitive.BooleanSerailizer;
import ru.snake.serde.serializer.primitive.ByteSerailizer;
import ru.snake.serde.serializer.primitive.CharacterSerailizer;
import ru.snake.serde.serializer.primitive.DoubleSerailizer;
import ru.snake.serde.serializer.primitive.FloatSerailizer;
import ru.snake.serde.serializer.primitive.IntegerSerailizer;
import ru.snake.serde.serializer.primitive.LongSerailizer;
import ru.snake.serde.serializer.primitive.ShortSerailizer;
import ru.snake.serde.stream.CompactInputStream;
import ru.snake.serde.stream.CompactOutputStream;
import ru.snake.serde.stream.DataInputBuilder;
import ru.snake.serde.stream.DataOutputBuilder;

public class Serde {

	private static final DataInputBuilder COMPACT_INPUT_STREAM = CompactInputStream::new;

	private static final DataOutputBuilder COMPACT_OUTPUT_STREAM = CompactOutputStream::new;

	private static final DataInputBuilder DATA_INPUT_STREAM = DataInputStream::new;

	private static final DataOutputBuilder DATA_OUTPUT_STREAM = DataOutputStream::new;

	private static final SerializeContextBuilder FLAT_SERIALIZE_CONTEXT = FlatSerializeContext::new;

	private static final DeserializeContextBuilder FLAT_DESERIALIZE_CONTEXT = FlatDeserializeContext::new;

	private static final SerializeContextBuilder REFERENCE_SERIALIZE_CONTEXT = ReferenceSerializeContext::new;

	private static final DeserializeContextBuilder REFERENCE_DESERIALIZE_CONTEXT = ReferenceDeserializeContext::new;

	private final TypeRegistry typeRegistry;

	private final SerializerRegistry serializerRegistry;

	private DataInputBuilder inputStream;

	private DataOutputBuilder outputStream;

	private SerializeContextBuilder serializeContext;

	private DeserializeContextBuilder deserializeContext;

	public Serde() {
		this.typeRegistry = new TypeRegistry();
		this.serializerRegistry = SerializerRegistry.create();
		this.inputStream = DATA_INPUT_STREAM;
		this.outputStream = DATA_OUTPUT_STREAM;
		this.serializeContext = FLAT_SERIALIZE_CONTEXT;
		this.deserializeContext = FLAT_DESERIALIZE_CONTEXT;
	}

	public Serde compact(final boolean enable) {
		if (enable) {
			inputStream = COMPACT_INPUT_STREAM;
			outputStream = COMPACT_OUTPUT_STREAM;
		} else {
			inputStream = DATA_INPUT_STREAM;
			outputStream = DATA_OUTPUT_STREAM;
		}

		return this;
	}

	public Serde references(boolean enable) {
		if (enable) {
			serializeContext = REFERENCE_SERIALIZE_CONTEXT;
			deserializeContext = REFERENCE_DESERIALIZE_CONTEXT;
		} else {
			serializeContext = FLAT_SERIALIZE_CONTEXT;
			deserializeContext = FLAT_DESERIALIZE_CONTEXT;
		}

		return this;
	}

	@SafeVarargs
	private <T> Serde registerSerializer(final Serialiser<T> serializer, final Class<T>... classes)
			throws SerdeException {
		typeRegistry.registerAll(classes);

		for (Class<T> clazz : classes) {
			serializerRegistry.register(clazz, serializer);
		}

		return this;
	}

	public Serde registerDefault() throws SerdeException {
		// Trivial types.
		registerSerializer(new ByteSerailizer(), byte.class, Byte.class);
		registerSerializer(new ShortSerailizer(), short.class, Short.class);
		registerSerializer(new IntegerSerailizer(), int.class, Integer.class);
		registerSerializer(new LongSerailizer(), long.class, Long.class);
		registerSerializer(new FloatSerailizer(), float.class, Float.class);
		registerSerializer(new DoubleSerailizer(), double.class, Double.class);
		registerSerializer(new BooleanSerailizer(), boolean.class, Boolean.class);
		registerSerializer(new CharacterSerailizer(), char.class, Character.class);

		// Basic types.
		registerSerializer(new StringSerailizer(), String.class);
		registerSerializer(new CollectionSerailizer<>(ArrayList::new), ArrayList.class);
		registerSerializer(new CollectionSerailizer<>(LinkedList::new), LinkedList.class);
		registerSerializer(new CollectionSerailizer<>(HashSet::new), HashSet.class);
		registerSerializer(new CollectionSerailizer<>(TreeSet::new), TreeSet.class);
		registerSerializer(new MapSerailizer<>(HashMap::new), HashMap.class);
		registerSerializer(new MapSerailizer<>(TreeMap::new), TreeMap.class);

		// Array types.
		registerSerializer(new ByteArraySerailizer(), byte[].class);
		registerSerializer(new ShortArraySerailizer(), short[].class);
		registerSerializer(new IntegerArraySerailizer(), int[].class);
		registerSerializer(new LongArraySerailizer(), long[].class);
		registerSerializer(new FloatArraySerailizer(), float[].class);
		registerSerializer(new DoubleArraySerailizer(), double[].class);
		registerSerializer(new BooleanArraySerailizer(), boolean[].class);
		registerSerializer(new CharacterArraySerailizer(), char[].class);
		registerSerializer(new ObjectArraySerailizer(), Object[].class);

		return this;
	}

	public <T> Serde register(final Class<T> clazz) throws SerdeException {
		Serialiser<T> serialiser = Serialiser.builder(clazz).build();

		typeRegistry.registerAll(clazz);
		serializerRegistry.register(clazz, serialiser);

		return this;
	}

	public <T> Serde register(final Class<T> clazz, final Serialiser<T> serialiser) throws SerdeException {
		typeRegistry.registerAll(clazz);
		serializerRegistry.register(clazz, serialiser);

		return this;
	}

	public <T> Serde register(final Class<T> clazz, final boolean recursive) throws SerdeException {
		if (recursive) {
			registerRecursive(clazz);
		} else {
			register(clazz);
		}

		return this;
	}

	private <T> void registerRecursive(final Class<T> clazz) throws SerdeException {
		@SuppressWarnings("unchecked")
		Class<Object> root = (Class<Object>) clazz;
		ArrayDeque<Class<Object>> deque = new ArrayDeque<>();
		Set<Class<Object>> visited = new HashSet<>();
		deque.addLast(root);

		while (!deque.isEmpty()) {
			Class<Object> current = deque.pollFirst();

			if (!visited.add(current)) {
				continue;
			}

			if (current.isPrimitive()) {
				// Primitive types must be registered using registerDefault().
				continue;
			} else if (current.isInterface()) {
				// We have to register all field types before serialization.
				// Type can be array of interfaces of abstract classes.
				if (!typeRegistry.contains(current)) {
					typeRegistry.registerOne(current);
				}

				continue;
			} else if (current.isEnum()) {
				@SuppressWarnings("rawtypes")
				Class<Enum> enumClass = cast(current);
				register(enumClass, new EnumerationSerailizer<>(enumClass));
			} else if (current.isArray()) {
				Class<Object[]> arrayClass = cast(current);
				Class<Object> componentClass = cast(arrayClass.getComponentType());

				typeRegistry.registerOne(arrayClass);
				serializerRegistry.register(arrayClass, new ObjectArraySerailizer());

				if (!serializerRegistry.contains(componentClass)) {
					deque.addLast(componentClass);

					for (Class<Object> next : collectFieldClasses(componentClass)) {
						if (!serializerRegistry.contains(next)) {
							deque.addLast(next);
						}
					}
				}
			} else {
				Serialiser<Object> serialiser = Serialiser.builder(current).build();

				typeRegistry.registerOne(current);
				serializerRegistry.register(current, serialiser);

				for (Class<Object> next : collectFieldClasses(current)) {
					if (!serializerRegistry.contains(next)) {
						deque.addLast(next);
					}
				}
			}
		}
	}

	private static Collection<Class<Object>> collectFieldClasses(Class<Object> clazz) {
		List<Class<Object>> result = new ArrayList<>();
		Class<Object> current = clazz;

		while (current != null) {
			for (Field field : current.getDeclaredFields()) {
				if ((field.getModifiers() & Modifier.STATIC) == 0) {
					Class<Object> type = cast(field.getType());

					result.add(type);
				}
			}

			current = current.getSuperclass();
		}

		return result;
	}

	private static <T> T cast(Object object) {
		@SuppressWarnings("unchecked")
		T result = (T) object;

		return result;
	}

	public <T> byte[] serialize(final T object) throws IOException, SerdeException {
		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			DataOutput stream = outputStream.create(buffer);
			SerializeContext context = serializeContext.create(typeRegistry, serializerRegistry);
			context.serialize(stream, object);

			return buffer.toByteArray();
		}
	}

	public <T> T deserialize(final byte[] bytes) throws IOException, SerdeException {
		try (ByteArrayInputStream buffer = new ByteArrayInputStream(bytes)) {
			DataInput stream = inputStream.create(buffer);
			DeserializeContext context = deserializeContext.create(typeRegistry, serializerRegistry);

			return context.deserialize(stream);
		}
	}

	public <T> void serialize(final DataOutput stream, final T object) throws IOException, SerdeException {
		SerializeContext context = serializeContext.create(typeRegistry, serializerRegistry);
		context.serialize(stream, object);
	}

	public <T> T deserialize(final DataInput stream) throws IOException, SerdeException {
		DeserializeContext context = deserializeContext.create(typeRegistry, serializerRegistry);

		return context.deserialize(stream);
	}

	@Override
	public String toString() {
		return "Serde [typeRegistry=" + typeRegistry + ", serializerRegistry=" + serializerRegistry + ", inputStream="
				+ inputStream + ", outputStream=" + outputStream + ", serializeContext=" + serializeContext
				+ ", deserializeContext=" + deserializeContext + "]";
	}

}
