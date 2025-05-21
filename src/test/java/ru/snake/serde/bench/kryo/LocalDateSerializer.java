package ru.snake.serde.bench.kryo;

import java.time.LocalDate;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class LocalDateSerializer extends Serializer<LocalDate> {

	@Override
	public void write(Kryo kryo, Output output, LocalDate object) {
		int year = object.getYear();
		short month = (short) object.getMonthValue();
		short day = (short) object.getDayOfMonth();

		output.writeInt(year);
		output.writeShort(month);
		output.writeShort(day);
	}

	@Override
	public LocalDate read(Kryo kryo, Input input, Class<? extends LocalDate> type) {
		int year = input.readInt();
		short month = input.readShort();
		short day = input.readShort();

		return LocalDate.of(year, month, day);
	}

}
