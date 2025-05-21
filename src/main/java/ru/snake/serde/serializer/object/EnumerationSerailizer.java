package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class EnumerationSerailizer<T extends Enum<T>> extends Serialiser<T> {

	private final Class<T> clazz;

	public EnumerationSerailizer(final Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final T object)
			throws IOException {
		stream.writeInt(object.ordinal());
	}

	@Override
	public T deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		int ordinal = stream.readInt();
		T result = clazz.getEnumConstants()[ordinal];

		return result;
	}

	@Override
	public String toString() {
		return "EnumerationSerailizer [clazz=" + clazz + "]";
	}

}
