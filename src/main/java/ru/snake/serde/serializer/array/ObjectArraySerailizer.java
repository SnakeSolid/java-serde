package ru.snake.serde.serializer.array;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class ObjectArraySerailizer extends Serialiser<Object[]> {

	@Override
	public void serialize(final SerdeContext context, final DataOutput stream, final Object[] array)
			throws IOException, SerdeException {
		Class<?> arrayClass = array.getClass().getComponentType();
		context.serializeType(stream, arrayClass);

		stream.writeInt(array.length);

		for (Object item : array) {
			context.serialize(stream, item);
		}
	}

	@Override
	public Object[] deserialize(final SerdeContext context, final DataInput stream) throws IOException, SerdeException {
		Class<?> clazz = context.deserializeType(stream);
		int length = stream.readInt();
		Object[] result = (Object[]) Array.newInstance(clazz, length);

		for (int index = 0; index < length; index += 1) {
			Object item = context.deserialize(stream);

			result[index] = item;
		}

		return result;
	}

	@Override
	public String toString() {
		return "ObjectArraySerailizer []";
	}

}
