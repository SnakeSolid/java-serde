package ru.snake.serde.array;

import java.util.Arrays;
import java.util.List;

public class Data {

	private final long[] ids;

	private final String[] keys;

	private final List<String>[] inner;

	public Data() {
		this.ids = null;
		this.keys = null;
		this.inner = null;
	}

	public Data(final long[] ids, final String[] keys, final List<String>[] inner) {
		this.ids = ids;
		this.keys = keys;
		this.inner = inner;
	}

	public long[] getIds() {
		return ids;
	}

	public String[] getKeys() {
		return keys;
	}

	public List<String>[] getInner() {
		return inner;
	}

	@Override
	public String toString() {
		return "Data [ids=" + Arrays.toString(ids) + ", keys=" + Arrays.toString(keys) + ", inner="
				+ Arrays.toString(inner) + "]";
	}

}
