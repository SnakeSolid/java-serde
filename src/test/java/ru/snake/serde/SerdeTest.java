package ru.snake.serde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

		Assertions.assertArrayEquals(source.getKeys(), target.getKeys());
		Assertions.assertEquals(source.getValue(), target.getValue());
		Assertions.assertEquals(source.getId(), target.getId());
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

		Assertions.assertEquals(source.getKeys(), target.getKeys());
		Assertions.assertEquals(source.getMap(), target.getMap());
	}

	@Test
	public void mustSerializeObjectArrays() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.array.Data.class);

		@SuppressWarnings("unchecked")
		ru.snake.serde.array.Data source = new ru.snake.serde.array.Data(
			new long[] { 100, 200, 300 },
			new String[] { "index", "name" },
			new List[] { list("a", "b"), list("cc", "dd") }
		);
		byte[] bytes = serde.serialize(source);
		ru.snake.serde.array.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source.getIds(), target.getIds());
		Assertions.assertEquals(source.getKeys(), target.getKeys());
		Assertions.assertEquals(source.getInner(), target.getInner());
	}

	@SafeVarargs
	private <T> List<T> list(final T... items) {
		return new ArrayList<>(Arrays.asList(items));
	}

}
