package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class CollectionSerailizer<T extends Collection<Object>> extends Serialiser<T> {

	private final Supplier<T> constructor;

	public CollectionSerailizer(final Supplier<T> constructor) {
		this.constructor = constructor;
	}

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final T collection)
			throws IOException, SerdeException {
		int length = collection.size();
		stream.writeInt(length);

		for (Object item : collection) {
			context.serialize(stream, item);
		}
	}

	@Override
	public T deserialize(final DeserializeContext context, final DataInput stream) throws IOException, SerdeException {
		int length = stream.readInt();
		T result = constructor.get();
		context.addObject(result);

		for (int index = 0; index < length; index += 1) {
			Object item = context.deserialize(stream);

			result.add(item);
		}

		return result;
	}

	@Override
	public String toString() {
		return "CollectionSerailizer [constructor=" + constructor + "]";
	}

}
