package ru.snake.serde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		Assertions.assertArrayEquals(source.getIds(), target.getIds());
		Assertions.assertArrayEquals(source.getKeys(), target.getKeys());
		Assertions.assertArrayEquals(source.getInner(), target.getInner());
		Assertions.assertArrayEquals(source.getOuter(), target.getOuter());
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

		Assertions.assertEquals(source.getType(), target.getType());
		Assertions.assertArrayEquals(source.getTags(), target.getTags());
	}

	@Test
	public void mustRegisterManyClasses() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(ru.snake.serde.parent.Data.class, true);
		serde.register(ru.snake.serde.collection.Data.class, true);
		serde.register(ru.snake.serde.array.Data.class, true);
	}

	@SafeVarargs
	private <T> List<T> list(final T... items) {
		return new ArrayList<>(Arrays.asList(items));
	}

}
