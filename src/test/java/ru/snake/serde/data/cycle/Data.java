package ru.snake.serde.data.cycle;

import java.util.Objects;

public class Data {

	private int value;

	private Data next;

	public Data() {
		this.value = 0;
		this.next = null;
	}

	public Data(final int value, final Data next) {
		this.value = value;
		this.next = next;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Data getNext() {
		return next;
	}

	public void setNext(Data next) {
		this.next = next;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Data other = (Data) obj;

		return value == other.value;
	}

	@Override
	public String toString() {
		return "Data [value=" + value + ", next=...]";
	}

}
