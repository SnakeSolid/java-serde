package ru.snake.serde.enums;

import java.util.Arrays;
import java.util.Objects;

public class Data {

	private final DataType type;

	private final DataTag[] tags;

	public Data() {
		this.type = null;
		this.tags = null;
	}

	public Data(final DataType type, final DataTag[] tags) {
		this.type = type;
		this.tags = tags;
	}

	public DataType getType() {
		return type;
	}

	public DataTag[] getTags() {
		return tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(tags);
		result = prime * result + Objects.hash(type);

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

		return Arrays.equals(tags, other.tags) && type == other.type;
	}

	@Override
	public String toString() {
		return "Data [type=" + type + ", tags=" + Arrays.toString(tags) + "]";
	}

}
