package ru.snake.serde.array;

import java.util.Arrays;
import java.util.List;

public class Data {

	private final long[] ids;

	private final String[] keys;

	private final List<String>[] inner;

	private final List<String>[] outer;

	public Data() {
		this.ids = null;
		this.keys = null;
		this.inner = null;
		this.outer = null;
	}

	public Data(final long[] ids, final String[] keys, final List<String>[] inner, final List<String>[] outer) {
		this.ids = ids;
		this.keys = keys;
		this.inner = inner;
		this.outer = outer;
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

	public List<String>[] getOuter() {
		return outer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(ids);
		result = prime * result + Arrays.hashCode(inner);
		result = prime * result + Arrays.hashCode(keys);
		result = prime * result + Arrays.hashCode(outer);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Data other = (Data) obj;

		return Arrays.equals(ids, other.ids) && Arrays.equals(inner, other.inner) && Arrays.equals(keys, other.keys)
				&& Arrays.equals(outer, other.outer);
	}

	@Override
	public String toString() {
		return "Data [ids=" + Arrays.toString(ids) + ", keys=" + Arrays.toString(keys) + ", inner="
				+ Arrays.toString(inner) + ", outer=" + Arrays.toString(outer) + "]";
	}

}
