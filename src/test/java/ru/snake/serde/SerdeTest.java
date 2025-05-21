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

import ru.snake.serde.data.enums.DataTag;
import ru.snake.serde.data.enums.DataType;

public class SerdeTest {

	@Test
	public void mustSerializeParentClassFields() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.data.parent.Data.class);

		ru.snake.serde.data.parent.Data source = new ru.snake.serde.data.parent.Data(
			new boolean[] { false, true, false },
			"hello",
			42
		);
		byte[] bytes = serde.serialize(source); // 32/14 bytes
		ru.snake.serde.data.parent.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source, target);
	}

	@Test
	public void mustSerializeBasicCollections() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.data.collection.Data.class);

		ru.snake.serde.data.collection.Data source = new ru.snake.serde.data.collection.Data(
			list("index", "name"),
			new HashMap<>(Map.of(1, 0.75f, 2, 1.44f))
		);
		byte[] bytes = serde.serialize(source); // 77/32 bytes
		ru.snake.serde.data.collection.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source, target);
	}

	@Test
	public void mustSerializeObjectArrays() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.data.array.Data.class, true);

		@SuppressWarnings("unchecked")
		ru.snake.serde.data.array.Data source = new ru.snake.serde.data.array.Data(
			new long[] { 100, 200, 300 },
			new String[] { "index", "name" },
			new List[] { list("a", "b"), list("cc", "dd") },
			new List[] { list("eee"), list("fff") }
		);
		byte[] bytes = serde.serialize(source); // 189/63 bytes
		ru.snake.serde.data.array.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source, target);
	}

	@Test
	public void mustSerializeEnums() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.data.enums.Data.class, true);

		ru.snake.serde.data.enums.Data source = new ru.snake.serde.data.enums.Data(
			DataType.TypeTwo,
			new DataTag[] { DataTag.Right, DataTag.Middle }
		);
		byte[] bytes = serde.serialize(source); // 40/10 bytes
		ru.snake.serde.data.enums.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source, target);
	}

	@Test
	public void mustSerilizeToStream() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.data.stream.Data.class, true);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ru.snake.serde.data.stream.Data sourceA = new ru.snake.serde.data.stream.Data(false, (short) 5, set("a", "b"));
		ru.snake.serde.data.stream.Data sourceB = new ru.snake.serde.data.stream.Data(true, (short) 42, set("c", "d"));

		try (DataOutputStream stream = new DataOutputStream(output)) {
			serde.serialize(stream, sourceA);
			serde.serialize(stream, sourceB);
		}

		ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
		ru.snake.serde.data.stream.Data targetA; // 74/24 bytes
		ru.snake.serde.data.stream.Data targetB;

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
		serde.register(ru.snake.serde.data.array.Data.class, true);
		serde.register(ru.snake.serde.data.collection.Data.class, true);
		serde.register(ru.snake.serde.data.enums.Data.class, true);
		serde.register(ru.snake.serde.data.parent.Data.class, true);
		serde.register(ru.snake.serde.data.stream.Data.class, true);
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
