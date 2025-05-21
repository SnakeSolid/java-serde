package ru.snake.serde.data.parent;

import java.util.Objects;

public class Data extends DataParent {

	private String value;

	public Data() {
	}

	public Data(
		String value,
		boolean booleanValue,
		byte byteValue,
		short shortValue,
		int intValue,
		long longValue,
		float floatValue,
		double doubleValue,
		char charValue
	) {
		super(booleanValue, byteValue, shortValue, intValue, longValue, floatValue, doubleValue, charValue);

		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(value);

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

		return super.equals(obj) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "Data [value=" + value + "]";
	}

}
