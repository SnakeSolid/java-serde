package ru.snake.serde.data.object;

import java.util.List;
import java.util.Objects;

public class Data {

	private int value;

	private List<String> keys;

	private List<List<String>> lists;

	public Data() {
		this.value = 0;
		this.keys = null;
		this.lists = null;
	}

	public Data(final int value, final List<String> keys, final List<List<String>> lists) {
		this.value = value;
		this.keys = keys;
		this.lists = lists;
	}

	@Override
	public int hashCode() {
		return Objects.hash(keys, lists, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Data other = (Data) obj;

		return Objects.equals(keys, other.keys) && Objects.equals(lists, other.lists) && value == other.value;
	}

	@Override
	public String toString() {
		return "Data [value=" + value + ", keys=" + keys + ", lists=" + lists + "]";
	}

}
