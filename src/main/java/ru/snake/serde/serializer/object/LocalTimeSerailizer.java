package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.LocalTime;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class LocalTimeSerailizer extends Serialiser<LocalTime> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final LocalTime object)
			throws IOException {
		byte hour = (byte) object.getHour();
		byte minute = (byte) object.getMinute();
		byte second = (byte) object.getSecond();
		int nano = object.getNano();

		stream.writeByte(hour);
		stream.writeByte(minute);
		stream.writeByte(second);
		stream.writeInt(nano);
	}

	@Override
	public LocalTime deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		byte hour = stream.readByte();
		byte minute = stream.readByte();
		byte second = stream.readByte();
		int nano = stream.readInt();

		return LocalTime.of(hour, minute, second, nano);
	}

	@Override
	public String toString() {
		return "LocalTimeSerailizer []";
	}

}
