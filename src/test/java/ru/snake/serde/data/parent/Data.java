package ru.snake.serde.data.parent;

import java.util.Arrays;
import java.util.Objects;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(keys);
		result = prime * result + Objects.hash(id, value);

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

		return id == other.id && Arrays.equals(keys, other.keys) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "Data [keys=" + Arrays.toString(keys) + ", value=" + value + ", id=" + id + "]";
	}

}
