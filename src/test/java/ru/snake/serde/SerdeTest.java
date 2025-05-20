package ru.snake.serde;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.snake.serde.enums.DataTag;
import ru.snake.serde.enums.DataType;

public class SerdeTest {

	@Test
	public void mustSerializeParentClassFields() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.parent.Data.class);

		ru.snake.serde.parent.Data source = new ru.snake.serde.parent.Data(
			new boolean[] { false, true, false },
			"hello",
			42
		);
		byte[] bytes = serde.serialize(source);
		ru.snake.serde.parent.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source, target);
	}

	@Test
	public void mustSerializeBasicCollections() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.collection.Data.class);

		ru.snake.serde.collection.Data source = new ru.snake.serde.collection.Data(
			list("index", "name"),
			new HashMap<>(Map.of(1, 0.75f, 2, 1.44f))
		);
		byte[] bytes = serde.serialize(source);
		ru.snake.serde.collection.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source, target);
	}

	@Test
	public void mustSerializeObjectArrays() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.array.Data.class, true);

		@SuppressWarnings("unchecked")
		ru.snake.serde.array.Data source = new ru.snake.serde.array.Data(
			new long[] { 100, 200, 300 },
			new String[] { "index", "name" },
			new List[] { list("a", "b"), list("cc", "dd") },
			new List[] { list("eee"), list("fff") }
		);
		byte[] bytes = serde.serialize(source);
		ru.snake.serde.array.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source, target);
	}

	@Test
	public void mustSerializeEnums() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.enums.Data.class, true);

		ru.snake.serde.enums.Data source = new ru.snake.serde.enums.Data(
			DataType.TypeTwo,
			new DataTag[] { DataTag.Right, DataTag.Middle }
		);
		byte[] bytes = serde.serialize(source);
		ru.snake.serde.enums.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source, target);
	}

	@Test
	public void mustSerilizeToStream() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.stream.Data.class, true);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ru.snake.serde.stream.Data sourceA = new ru.snake.serde.stream.Data(false, (short) 5, set("a", "b"));
		ru.snake.serde.stream.Data sourceB = new ru.snake.serde.stream.Data(true, (short) 42, set("c", "d"));

		try (DataOutputStream stream = new DataOutputStream(output)) {
			serde.serialize(stream, sourceA);
			serde.serialize(stream, sourceB);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
		ru.snake.serde.stream.Data targetA;
		ru.snake.serde.stream.Data targetB;

		try (DataInputStream stream = new DataInputStream(input)) {
			targetA = serde.deserialize(stream);
			targetB = serde.deserialize(stream);
		}

		Assertions.assertEquals(sourceA, targetA);
		Assertions.assertEquals(sourceB, targetB);
	}

	@Test
	public void mustRegisterManyClasses() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.array.Data.class, true);
		serde.register(ru.snake.serde.collection.Data.class, true);
		serde.register(ru.snake.serde.enums.Data.class, true);
		serde.register(ru.snake.serde.parent.Data.class, true);
		serde.register(ru.snake.serde.stream.Data.class, true);
	}

	@SafeVarargs
	private <T> Set<T> set(final T... items) {
		return new HashSet<>(Arrays.asList(items));
	}

	@SafeVarargs
	private <T> List<T> list(final T... items) {
		return new ArrayList<>(Arrays.asList(items));
	}

}
