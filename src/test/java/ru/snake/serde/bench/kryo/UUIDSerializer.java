package ru.snake.serde.bench.kryo;

import java.util.UUID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class UUIDSerializer extends Serializer<UUID> {

	@Override
	public void write(Kryo kryo, Output output, UUID object) {
		long highPart = object.getMostSignificantBits();
		long lowPart = object.getLeastSignificantBits();

		output.writeLong(highPart);
		output.writeLong(lowPart);
	}

	@Override
	public UUID read(Kryo kryo, Input input, Class<? extends UUID> type) {
		long highPart = input.readLong();
		long lowPart = input.readLong();

		return new UUID(highPart, lowPart);
	}

}
