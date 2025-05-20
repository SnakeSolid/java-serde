package ru.snake.serde.serializer.object;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.function.Supplier;

import ru.snake.serde.context.SerdeContext;
import ru.snake.serde.serializer.Serialiser;
import ru.snake.serde.serializer.exception.SerdeException;

public class CollectionSerailizer<T extends Collection<Object>> extends Serialiser<T> {

	private final Supplier<T> constructor;

	public CollectionSerailizer(final Supplier<T> constructor) {
		this.constructor = constructor;
	}

	@Override
	public void serialize(final SerdeContext context, final DataOutputStream stream, final T collection)
			throws IOException, SerdeException {
		int length = collection.size();
		stream.writeInt(length);

		for (Object item : collection) {
			context.serialize(stream, item);
		}
	}

	@Override
	public T deserialize(final SerdeContext context, final DataInputStream stream) throws IOException, SerdeException {
		int length = stream.readInt();
		T result = constructor.get();

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
