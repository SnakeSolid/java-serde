package ru.snake.serde.data.parent;

import java.util.Objects;

public class DataParent {

	protected boolean booleanValue;

	protected byte byteValue;

	protected short shortValue;

	protected int intValue;

	protected long longValue;

	protected float floatValue;

	protected double doubleValue;

	protected char charValue;

	public DataParent() {
		this.booleanValue = false;
		this.byteValue = 0;
		this.shortValue = 0;
		this.intValue = 0;
		this.longValue = 0;
		this.floatValue = 0;
		this.doubleValue = 0;
		this.charValue = 0;
	}

	public DataParent(
		boolean booleanValue,
		byte byteValue,
		short shortValue,
		int intValue,
		long longValue,
		float floatValue,
		double doubleValue,
		char charValue
	) {
		this.booleanValue = booleanValue;
		this.byteValue = byteValue;
		this.shortValue = shortValue;
		this.intValue = intValue;
		this.longValue = longValue;
		this.floatValue = floatValue;
		this.doubleValue = doubleValue;
		this.charValue = charValue;
	}

	@Override
	public int hashCode() {
		return Objects
			.hash(booleanValue, byteValue, charValue, doubleValue, floatValue, intValue, longValue, shortValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		DataParent other = (DataParent) obj;

		return booleanValue == other.booleanValue && byteValue == other.byteValue && charValue == other.charValue
				&& Double.doubleToLongBits(doubleValue) == Double.doubleToLongBits(other.doubleValue)
				&& Float.floatToIntBits(floatValue) == Float.floatToIntBits(other.floatValue)
				&& intValue == other.intValue && longValue == other.longValue && shortValue == other.shortValue;
	}

	@Override
	public String toString() {
		return "DataParent [booleanValue=" + booleanValue + ", byteValue=" + byteValue + ", shortValue=" + shortValue
				+ ", intValue=" + intValue + ", longValue=" + longValue + ", floatValue=" + floatValue
				+ ", doubleValue=" + doubleValue + ", charValue=" + charValue + "]";
	}

}
