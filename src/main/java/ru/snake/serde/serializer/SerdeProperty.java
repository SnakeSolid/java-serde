package ru.snake.serde.serializer;

import java.lang.invoke.MethodHandle;

import ru.snake.serde.serializer.exception.SerdeReflectiveException;

public class SerdeProperty {

	private final String propertyName;

	private final Class<?> propertyType;

	private final MethodHandle getter;

	private final MethodHandle setter;

	public SerdeProperty(
		final String propertyName,
		final Class<?> propertyType,
		final MethodHandle getter,
		final MethodHandle setter
	) {
		this.propertyName = propertyName;
		this.propertyType = propertyType;
		this.getter = getter;
		this.setter = setter;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public <T> T get(final Object object) throws SerdeReflectiveException {
		try {
			return (T) getter.invoke(object);
		} catch (Throwable e) {
			String message = String.format(
				"Failed to read field value: class = %s, field = %s",
				object.getClass().getCanonicalName(),
				propertyName
			);

			throw new SerdeReflectiveException(message, e);
		}
	}

	public void set(final Object object, final Object value) throws SerdeReflectiveException {
		try {
			setter.invoke(object, value);
		} catch (Throwable e) {
			String message = String.format(
				"Failed to write field value: class = %s, field = %s",
				object.getClass().getCanonicalName(),
				propertyName
			);

			throw new SerdeReflectiveException(message, e);
		}
	}

	@Override
	public String toString() {
		return "SerdeProperty [propertyName=" + propertyName + ", propertyType=" + propertyType + ", getter=" + getter
				+ ", setter=" + setter + "]";
	}

}
