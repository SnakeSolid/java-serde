package ru.snake.serde;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import ru.snake.serde.serializer.SerdeContext;
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
import ru.snake.serde.serializer.exception.SerdeDuplicateClassException;
import ru.snake.serde.serializer.exception.SerdeException;
import ru.snake.serde.serializer.exception.SerdeReflectiveException;
import ru.snake.serde.serializer.object.CollectionSerailizer;
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

public class Serde {

	private final TypeRegistry typeRegistry;

	private final SerializerRegistry serializerRegistry;

	public Serde() {
		this.typeRegistry = new TypeRegistry();
		this.serializerRegistry = SerializerRegistry.create();
	}

	@SafeVarargs
	private <T> void registerSerializer(final Serialiser<T> serializer, final Class<T>... classes)
			throws SerdeException {
		typeRegistry.register(classes);

		for (Class<T> clazz : classes) {
			serializerRegistry.register(clazz, serializer);
		}
	}

	public void registerDefault() throws SerdeException {
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
	}

	public <T> void register(final Class<T> clazz) throws SerdeReflectiveException, SerdeDuplicateClassException {
		typeRegistry.register(clazz);
		serializerRegistry.register(clazz);
	}

	public <T> void register(final Class<T> clazz, final Serialiser<T> serialiser) throws SerdeException {
		typeRegistry.register(clazz);
		serializerRegistry.register(clazz, serialiser);
	}

	public <T> byte[] serialize(final T object) throws IOException, SerdeException {
		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				DataOutputStream stream = new DataOutputStream(buffer)) {
			SerdeContext context = new SerdeContext(typeRegistry, serializerRegistry);
			context.serialize(stream, object);

			return buffer.toByteArray();
		}
	}

	public <T> T deserialize(final byte[] bytes) throws IOException, SerdeException {
		try (ByteArrayInputStream buffer = new ByteArrayInputStream(bytes);
				DataInputStream stream = new DataInputStream(buffer)) {
			SerdeContext context = new SerdeContext(typeRegistry, serializerRegistry);

			return context.deserialize(stream);
		}
	}

	@Override
	public String toString() {
		return "Serde [typeRegistry=" + typeRegistry + "]";
	}

}
