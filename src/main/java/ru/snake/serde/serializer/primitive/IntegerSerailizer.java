package ru.snake.serde.serializer.primitive;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ru.snake.serde.context.DeserializeContext;
import ru.snake.serde.context.SerializeContext;
import ru.snake.serde.serializer.Serialiser;

public class IntegerSerailizer extends Serialiser<Integer> {

	@Override
	public void serialize(final SerializeContext context, final DataOutput stream, final Integer object)
			throws IOException {
		stream.writeInt(object);
	}

	@Override
	public Integer deserialize(final DeserializeContext context, final DataInput stream) throws IOException {
		return stream.readInt();
	}

	@Override
	public String toString() {
		return "IntegerSerailizer []";
	}

}
