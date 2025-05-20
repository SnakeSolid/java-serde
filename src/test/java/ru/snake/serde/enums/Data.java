package ru.snake.serde.enums;

import java.util.Arrays;

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
	public String toString() {
		return "Data [type=" + type + ", tags=" + Arrays.toString(tags) + "]";
	}

}
