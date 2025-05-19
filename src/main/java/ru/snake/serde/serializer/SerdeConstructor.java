package ru.snake.serde.serializer;

import java.lang.invoke.MethodHandle;

import ru.snake.serde.serializer.exception.SerdeReflectiveException;

public class SerdeConstructor<T> {

	private final MethodHandle constructor;

	public SerdeConstructor(final MethodHandle constructor) {
		this.constructor = constructor;
	}

	public T create() throws SerdeReflectiveException {
		try {
			return (T) constructor.invoke();
		} catch (Throwable e) {
			throw new SerdeReflectiveException("Failed to create class instanse", e);
		}
	}

	@Override
	public String toString() {
		return "SerdeConstructor [constructor=" + constructor + "]";
	}

}
