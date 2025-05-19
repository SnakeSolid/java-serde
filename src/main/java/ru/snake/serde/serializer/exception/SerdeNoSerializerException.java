package ru.snake.serde.serializer.exception;

public class SerdeNoSerializerException extends SerdeException {

	private static final long serialVersionUID = -6954122454432330466L;

	public SerdeNoSerializerException() {
		super();
	}

	public SerdeNoSerializerException(String message) {
		super(message);
	}

	public SerdeNoSerializerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerdeNoSerializerException(Throwable cause) {
		super(cause);
	}

}
