package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.LocalDateTime;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class LocalDateTimeSerailizer extends Serialiser<LocalDateTime> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final LocalDateTime object)
			throws IOException {
		int year = object.getYear();
		short month = (short) object.getMonthValue();
		short day = (short) object.getDayOfMonth();
		byte hour = (byte) object.getHour();
		byte minute = (byte) object.getMinute();
		byte second = (byte) object.getSecond();
		int nano = object.getNano();

		stream.writeInt(year);
		stream.writeShort(month);
		stream.writeShort(day);
		stream.writeByte(hour);
		stream.writeByte(minute);
		stream.writeByte(second);
		stream.writeInt(nano);
	}

	@Override
	public LocalDateTime deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		int year = stream.readInt();
		short month = stream.readShort();
		short day = stream.readShort();
		byte hour = stream.readByte();
		byte minute = stream.readByte();
		byte second = stream.readByte();
		int nano = stream.readInt();

		return LocalDateTime.of(year, month, day, hour, minute, second, nano);
	}

	@Override
	public String toString() {
		return "LocalDateTimeSerailizer []";
	}

}
