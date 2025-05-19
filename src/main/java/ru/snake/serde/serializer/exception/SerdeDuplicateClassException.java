package ru.snake.serde.serializer.exception;

public class SerdeDuplicateClassException extends SerdeException {

	private static final long serialVersionUID = -6471202427164018899L;

	public SerdeDuplicateClassException() {
		super();
	}

	public SerdeDuplicateClassException(String message) {
		super(message);
	}

	public SerdeDuplicateClassException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerdeDuplicateClassException(Throwable cause) {
		super(cause);
	}

}
