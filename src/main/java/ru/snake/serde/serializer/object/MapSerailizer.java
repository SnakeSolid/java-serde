package ru.snake.serde.serializer.object;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import ru.snake.serde.serializer.SerdeContext;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class MapSerailizer<T extends Map<Object, Object>> extends Serialiser<T> {

	private final Supplier<T> constructor;

	public MapSerailizer(final Supplier<T> constructor) {
		this.constructor = constructor;
	}

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final T collection)
			throws IOException, SerdeException {
		int length = collection.size();
		stream.writeInt(length);

		for (Entry<Object, Object> entry : collection.entrySet()) {
			context.serialize(stream, entry.getKey());
			context.serialize(stream, entry.getValue());
		}
	}

	@Override
	public T deserialize(final SerdeContext context, final DataInputStream stream) throws IOException, SerdeException {
		int length = stream.readInt();
		T result = constructor.get();

		for (int index = 0; index < length; index += 1) {
			Object key = context.deserialize(stream);
			Object value = context.deserialize(stream);

			result.put(key, value);
		}

		return result;
	}

	@Override
	public String toString() {
		return "CollectionSerailizer [constructor=" + constructor + "]";
	}

}
