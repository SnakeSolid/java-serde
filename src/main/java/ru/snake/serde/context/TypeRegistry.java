package ru.snake.serde.context;

import java.util.HashMap;
import java.util.Map;

import ru.snake.serde.serializer.exception.SerdeDuplicateClassException;
import ru.snake.serde.serializer.exception.SerdeNoSuchClassException;

public class TypeRegistry {

	private static final int ID_REFERENCE = -1;

	private static final int ID_NULL = 0;

	private static final int ID_START = 1;

	private final Map<Class<?>, Integer> classToId;

	private final Map<Integer, Class<?>> idToClass;

	private int nextId;

	public TypeRegistry() {
		this.classToId = new HashMap<>();
		this.idToClass = new HashMap<>();
		this.nextId = ID_START;
	}

	public boolean contains(final Class<?> clazz) throws SerdeDuplicateClassException {
		return classToId.containsKey(clazz);
	}

	public int registerOne(final Class<?> clazz) throws SerdeDuplicateClassException {
		Integer result = classToId.get(clazz);

		if (result == null) {
			Integer id = nextId;
			nextId += 1;

			classToId.put(clazz, id);
			idToClass.put(id, clazz);

			return id;
		}

		return result;
	}

	public int registerAll(final Class<?>... classes) throws SerdeDuplicateClassException {
		Integer id = nextId;
		nextId += 1;

		for (Class<?> clazz : classes) {
			if (classToId.containsKey(clazz)) {
				throw new SerdeDuplicateClassException(
					String.format("Class %s already registered in registry.", clazz.getCanonicalName())
				);
			}

			classToId.put(clazz, id);
			idToClass.put(id, clazz);
		}

		return id;
	}

	public int getReferenceId() throws SerdeNoSuchClassException {
		return ID_REFERENCE;
	}

	public int getNullId() throws SerdeNoSuchClassException {
		return ID_NULL;
	}

	public int getId(final Class<?> clazz) throws SerdeNoSuchClassException {
		Integer id = classToId.get(clazz);

		if (id == null) {
			throw new SerdeNoSuchClassException(String.format("Class for id %s not found in registry.", clazz));
		}

		return id;
	}

	public <T> Class<T> getClass(final int id) throws SerdeNoSuchClassException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) idToClass.get(id);

		if (clazz == null) {
			throw new SerdeNoSuchClassException(String.format("Class for id %d not found in registry.", id));
		}

		return clazz;
	}

	@Override
	public String toString() {
		return "TypeRegistry [classIds=" + classToId + ", nextId=" + nextId + "]";
	}

}
