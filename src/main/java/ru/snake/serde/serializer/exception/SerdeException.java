package ru.snake.serde.serializer.exception;

public class SerdeException extends Exception {

	private static final long serialVersionUID = -5552300238053584692L;

	public SerdeException() {
		super();
	}

	public SerdeException(String message) {
		super(message);
	}

	public SerdeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerdeException(Throwable cause) {
		super(cause);
	}

}
