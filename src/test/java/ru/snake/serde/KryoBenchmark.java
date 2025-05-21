package ru.snake.serde;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import ru.snake.serde.bench.Company;
import ru.snake.serde.bench.Department;
import ru.snake.serde.bench.Employee;
import ru.snake.serde.bench.OrganizationType;
import ru.snake.serde.bench.Transaction;
import ru.snake.serde.bench.TransactionStatus;
import ru.snake.serde.bench.kryo.LocalDateSerializer;
import ru.snake.serde.bench.kryo.UUIDSerializer;
import ru.snake.serde.serializer.exception.SerdeException;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class KryoBenchmark {

	public static void main(String[] args) throws RunnerException, IOException {
		Options opt = new OptionsBuilder().include(KryoBenchmark.class.getSimpleName()).forks(1).build();

		new Runner(opt).run();
	}

	@Param({ "small", "medium", "large" })
	private String type;

	@Param({ "false", "true" })
	private boolean optimized;

	@Param({ "false", "true" })
	private boolean references;

	private Kryo kryo;

	private Company company;

	@Setup
	public void setup() throws SerdeException {
		kryo = new Kryo();
		kryo.register(UUID.class, new UUIDSerializer());
		kryo.register(LocalDate.class, new LocalDateSerializer());
		kryo.register(Company.class);
		kryo.register(Department[].class);
		kryo.register(Department.class);
		kryo.register(Transaction[].class);
		kryo.register(Transaction.class);
		kryo.register(Employee[].class);
		kryo.register(Employee.class);
		kryo.register(OrganizationType.class);
		kryo.register(TransactionStatus.class);
		kryo.setRegistrationRequired(true);
		kryo.setReferences(references);
		kryo.setOptimizedGenerics(optimized);

		switch (type) {
		case "small":
			company = Company.createCompany(1, 10, 1_000);
			break;

		case "medium":
			company = Company.createCompany(1, 100, 10_000);
			break;

		case "large":
			company = Company.createCompany(1, 1_000, 100_000);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	@Benchmark
	public void serializeOnly(final Blackhole blackhole) throws IOException, SerdeException {
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream(); Output output = new Output(stream)) {
			kryo.writeObject(output, company);

			blackhole.consume(stream.toByteArray());
		}
	}

	@Benchmark
	public void serializeDeserialize(final Blackhole blackhole) throws IOException, SerdeException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		try (Output output = new Output(buffer)) {
			kryo.writeObject(output, company);
		}

		try (ByteArrayInputStream stream = new ByteArrayInputStream(buffer.toByteArray());
				Input input = new Input(stream)) {
			Company target = kryo.readObject(input, Company.class);

			blackhole.consume(target);
		}
	}

}
