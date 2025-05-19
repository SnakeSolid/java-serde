package ru.snake.serde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
		serde.register(ru.snake.serde.collections.Data.class);

		ru.snake.serde.collections.Data source = new ru.snake.serde.collections.Data(
			new ArrayList<>(Arrays.asList("index", "name")),
			new HashMap<>(Map.of(1, 0.75f, 2, 1.44f))
		);
		byte[] bytes = serde.serialize(source);
		ru.snake.serde.collections.Data target = serde.deserialize(bytes);

		Assertions.assertEquals(source.getKeys(), target.getKeys());
		Assertions.assertEquals(source.getMap(), target.getMap());
	}

}
