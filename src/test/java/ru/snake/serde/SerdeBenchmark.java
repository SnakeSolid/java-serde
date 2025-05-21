package ru.snake.serde;

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

import ru.snake.serde.bench.Company;
import ru.snake.serde.serializer.exception.SerdeException;
import ru.snake.serde.serializer.object.LocalDateSerailizer;
import ru.snake.serde.serializer.object.UuidSerailizer;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class SerdeBenchmark {

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(SerdeBenchmark.class.getSimpleName()).forks(1).build();

		new Runner(opt).run();
	}

	@Param({ "small", "medium", "large" })
	private String type;

	@Param({ "false", "true" })
	private boolean compact;

	@Param({ "false", "true" })
	private boolean references;

	private Serde serde;

	private Company company;

	@Setup
	public void setup() throws SerdeException {
		serde = new Serde().registerDefault()
			.register(UUID.class, new UuidSerailizer())
			.register(LocalDate.class, new LocalDateSerailizer())
			.register(Company.class, true)
			.compact(compact)
			.references(references);

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
		byte[] bytes = serde.serialize(company);
		blackhole.consume(bytes);
	}

	@Benchmark
	public void serializeDeserialize(final Blackhole blackhole) throws IOException, SerdeException {
		byte[] bytes = serde.serialize(company);
		Company target = serde.deserialize(bytes);

		blackhole.consume(target);
	}

}
