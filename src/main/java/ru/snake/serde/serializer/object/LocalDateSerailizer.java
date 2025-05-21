package ru.snake.serde.serializer.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.LocalDate;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class LocalDateSerailizer extends Serialiser<LocalDate> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final LocalDate object)
			throws IOException {
		int year = object.getYear();
		short month = (short) object.getMonthValue();
		short day = (short) object.getDayOfMonth();

		stream.writeInt(year);
		stream.writeShort(month);
		stream.writeShort(day);
	}

	@Override
	public LocalDate deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		int year = stream.readInt();
		short month = stream.readShort();
		short day = stream.readShort();

		return LocalDate.of(year, month, day);
	}

	@Override
	public String toString() {
		return "LocalDateSerailizer []";
	}

}
