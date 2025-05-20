package ru.snake.serde.serializer.object;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ru.snake.serde.serializer.SerdeConstructor;
import ru.snake.serde.serializer.SerdeProperty;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeReflectiveException;
import ru.snake.serde.serializer.property.ProterySerializer;

public class ClassSerialiserBuilder<T> {

	private final Class<T> clazz;

	private SerdeConstructor<T> constructor;

	public ClassSerialiserBuilder(final Class<T> clazz) {
		this.clazz = clazz;
		this.constructor = null;
	}

	public ClassSerialiserBuilder<T> constructor(final SerdeConstructor<T> constructor) {
		this.constructor = constructor;

		return this;
	}

	public Serialiser<T> build() throws SerdeReflectiveException {
		List<ProterySerializer> serializers = new ArrayList<>();
		Class<?> current = clazz;

		while (current != null) {
			for (Field field : current.getDeclaredFields()) {
				try {
					field.setAccessible(true);

					MethodHandle getter = MethodHandles.lookup().unreflectGetter(field);
					MethodHandle setter = MethodHandles.lookup().unreflectSetter(field);
					SerdeProperty property = new SerdeProperty(field.getName(), field.getType(), getter, setter);
					ProterySerializer serializer = ProterySerializer.create(property);

					serializers.add(serializer);
				} catch (IllegalAccessException e) {
					throw new SerdeReflectiveException(e);
				}
			}

			current = current.getSuperclass();
		}

		SerdeConstructor<T> serdeConstructor;

		if (constructor == null) {
			try {
				Constructor<T> classConstructor = clazz.getConstructor();
				MethodHandle handle = MethodHandles.lookup().unreflectConstructor(classConstructor);
				serdeConstructor = new SerdeConstructor<>(handle);
			} catch (IllegalAccessException | NoSuchMethodException | SecurityException e) {
				throw new SerdeReflectiveException(e);
			}
		} else {
			serdeConstructor = constructor;
		}

		return new ClassSerializer<>(serdeConstructor, serializers);
	}

	@Override
	public String toString() {
		return "SerialiserBuilder [clazz=" + clazz + ", constructor=" + constructor + "]";
	}

}
