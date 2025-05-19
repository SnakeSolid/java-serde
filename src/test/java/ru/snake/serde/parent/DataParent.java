package ru.snake.serde.parent;

public class DataParent {

	protected int id;

	public DataParent() {
	}

	public DataParent(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "DataParent [id=" + id + "]";
	}

}
