package ru.snake.serde.collection;

import java.util.List;
import java.util.Map;

public class Data {

	private final List<String> keys;

	private final Map<Integer, Float> map;

	public Data() {
		this.keys = null;
		this.map = null;
	}

	public Data(final List<String> keys, final Map<Integer, Float> map) {
		this.keys = keys;
		this.map = map;
	}

	public List<String> getKeys() {
		return keys;
	}

	public Map<Integer, Float> getMap() {
		return map;
	}

	@Override
	public String toString() {
		return "Data [keys=" + keys + ", map=" + map + "]";
	}

}
