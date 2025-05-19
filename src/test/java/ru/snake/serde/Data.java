package ru.snake.serde;

import java.util.Arrays;

public class Data extends DataParent {

	private boolean[] keys;

	private String value;

	public Data() {
	}

	public Data(boolean[] keys, String value, int id) {
		super(id);

		this.keys = keys;
		this.value = value;
	}

	public boolean[] getKeys() {
		return keys;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Data [keys=" + Arrays.toString(keys) + ", value=" + value + ", id=" + id + "]";
	}

}
