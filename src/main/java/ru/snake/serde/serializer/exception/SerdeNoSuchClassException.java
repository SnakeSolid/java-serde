package ru.snake.serde.serializer.exception;

public class SerdeNoSuchClassException extends SerdeException {

	private static final long serialVersionUID = 5337835270229546226L;

	public SerdeNoSuchClassException() {
		super();
	}

	public SerdeNoSuchClassException(String message) {
		super(message);
	}

	public SerdeNoSuchClassException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerdeNoSuchClassException(Throwable cause) {
		super(cause);
	}

}
