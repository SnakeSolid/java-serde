package ru.snake.serde.stream;

import java.util.Objects;
import java.util.Set;

public class Data {

	private final boolean active;

	private final Short nIterations;

	private final Set<String> values;

	public Data() {
		this.active = false;
		this.nIterations = 0;
		this.values = null;
	}

	public Data(final boolean active, final Short nIterations, final Set<String> values) {
		this.active = active;
		this.nIterations = nIterations;
		this.values = values;
	}

	public boolean isActive() {
		return active;
	}

	public Short getnIterations() {
		return nIterations;
	}

	public Set<String> getValues() {
		return values;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, nIterations, values);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Data other = (Data) obj;

		return active == other.active && Objects.equals(nIterations, other.nIterations)
				&& Objects.equals(values, other.values);
	}

	@Override
	public String toString() {
		return "Data [active=" + active + ", nIterations=" + nIterations + ", values=" + values + "]";
	}

}
