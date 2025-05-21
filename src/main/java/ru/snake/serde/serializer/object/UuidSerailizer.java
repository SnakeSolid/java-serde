package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.UUID;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class UuidSerailizer extends Serialiser<UUID> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final UUID object)
			throws IOException {
		long highPart = object.getMostSignificantBits();
		long lowPart = object.getLeastSignificantBits();

		stream.writeLong(highPart);
		stream.writeLong(lowPart);
	}

	@Override
	public UUID deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		long highPart = stream.readLong();
		long lowPart = stream.readLong();

		return new UUID(highPart, lowPart);
	}

	@Override
	public String toString() {
		return "UuidSerailizer []";
	}

}
