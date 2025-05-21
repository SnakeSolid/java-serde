package ru.snake.serde.data.array;

import java.util.Arrays;
import java.util.List;

public class Data {

	private boolean[] booleans;

	private byte[] bytes;

	private short[] shorts;

	private int[] ints;

	private long[] longs;

	private float[] floats;

	private double[] doubles;

	private char[] chars;

	private String[] strings;

	private List<String>[] inner;

	private List<String>[] outer;

	public Data() {
		this.booleans = null;
		this.bytes = null;
		this.shorts = null;
		this.ints = null;
		this.longs = null;
		this.floats = null;
		this.doubles = null;
		this.chars = null;
		this.strings = null;
		this.inner = null;
		this.outer = null;
	}

	public Data(
		boolean[] booleans,
		byte[] bytes,
		short[] shorts,
		int[] ints,
		long[] longs,
		float[] floats,
		double[] doubles,
		char[] chars,
		String[] strings,
		List<String>[] inner,
		List<String>[] outer
	) {
		super();
		this.booleans = booleans;
		this.bytes = bytes;
		this.shorts = shorts;
		this.ints = ints;
		this.longs = longs;
		this.floats = floats;
		this.doubles = doubles;
		this.chars = chars;
		this.strings = strings;
		this.inner = inner;
		this.outer = outer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(booleans);
		result = prime * result + Arrays.hashCode(bytes);
		result = prime * result + Arrays.hashCode(chars);
		result = prime * result + Arrays.hashCode(doubles);
		result = prime * result + Arrays.hashCode(floats);
		result = prime * result + Arrays.hashCode(inner);
		result = prime * result + Arrays.hashCode(ints);
		result = prime * result + Arrays.hashCode(longs);
		result = prime * result + Arrays.hashCode(outer);
		result = prime * result + Arrays.hashCode(shorts);
		result = prime * result + Arrays.hashCode(strings);

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Data other = (Data) obj;

		return Arrays.equals(booleans, other.booleans) && Arrays.equals(bytes, other.bytes)
				&& Arrays.equals(chars, other.chars) && Arrays.equals(doubles, other.doubles)
				&& Arrays.equals(floats, other.floats) && Arrays.equals(inner, other.inner)
				&& Arrays.equals(ints, other.ints) && Arrays.equals(longs, other.longs)
				&& Arrays.equals(outer, other.outer) && Arrays.equals(shorts, other.shorts)
				&& Arrays.equals(strings, other.strings);
	}

	@Override
	public String toString() {
		return "Data [booleans=" + Arrays.toString(booleans) + ", bytes=" + Arrays.toString(bytes) + ", shorts="
				+ Arrays.toString(shorts) + ", ints=" + Arrays.toString(ints) + ", longs=" + Arrays.toString(longs)
				+ ", floats=" + Arrays.toString(floats) + ", doubles=" + Arrays.toString(doubles) + ", chars="
				+ Arrays.toString(chars) + ", strings=" + Arrays.toString(strings) + ", inner=" + Arrays.toString(inner)
				+ ", outer=" + Arrays.toString(outer) + "]";
	}

}
