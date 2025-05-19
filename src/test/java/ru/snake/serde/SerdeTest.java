package ru.snake.serde;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SerdeTest {

	@Test
	public void test() throws Throwable {
		Serde serde = new Serde();
		serde.registerDefault();
		serde.register(Data.class);

		Data source = new Data(new boolean[] { false, true, false }, "hello", 42);
		byte[] bytes = serde.serialize(source);
		Data target = serde.deserialize(bytes);

		Assertions.assertArrayEquals(source.getKeys(), target.getKeys());
		Assertions.assertEquals(source.getValue(), target.getValue());
		Assertions.assertEquals(source.getId(), target.getId());
	}

}
