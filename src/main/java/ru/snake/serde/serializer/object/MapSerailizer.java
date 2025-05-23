package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class MapSerailizer<T extends Map<Object, Object>> extends Serialiser<T> {

	private final Supplier<T> constructor;

	public MapSerailizer(final Supplier<T> constructor) {
		this.constructor = constructor;
	}

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final T collection)
			throws IOException, SerdeException {
		int length = collection.size();
		stream.writeInt(length);

		for (Entry<Object, Object> entry : collection.entrySet()) {
			context.serialize(stream, entry.getKey());
			context.serialize(stream, entry.getValue());
		}
	}

	@Override
	public T deserialize(final DeserializeContext context, final DataInput stream) throws IOException, SerdeException {
		int length = stream.readInt();
		T result = constructor.get();
		context.addObject(result);

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
