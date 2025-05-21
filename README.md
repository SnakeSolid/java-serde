# Serde Java Serialization Framework

A lightweight library for converting Java objects to byte streams and reconstructing them, supporting primitives, arrays, strings, and custom objects via reflection.

## Features

- [x] **Primitive Types**: Supports all Java primitives (`boolean`, `int`, `double`, etc.) and their wrapper classes.
- [x] **Arrays**: Serializes arrays of primitives (e.g., `int[]`, `boolean[]`).
- [x] **Strings**: Built-in support for `String` serialization.
- [x] **Custom Objects**: Serialize/deserialize custom classes by registering them. Handles inheritance and private fields.
- [x] **Compact Representation**: Support serialization in compact and full format.
- [x] **Cyclic References**: Support objects with cyclic and self references.

## Usage

### 1. Initialize Serde

Create a `Serde` instance and register default serializers:

```java
Serde serde = new Serde();
serde.registerDefault(); // Registers primitives, wrappers, strings, and arrays
serde.compact(true); // For serializing in compact format
```

### 2. Register Custom Classes

For custom classes, register them with the `Serde` instance:

```java
serde.register(Data.class); // Data includes fields from DataParent
```

**Requirements for Custom Classes:**
- Must have a public no-argument constructor (or specify a custom constructor).
- All fields (including inherited) must be serializable.

### 3. Serialize Objects

Convert objects to byte arrays:

```java
Data source = new Data("Hello", 42);
byte[] bytes = serde.serialize(source);
```

### 4. Deserialize Objects

Reconstruct objects from byte arrays:

```java
Data target = serde.deserialize(bytes);
```

## Custom Serialization

To use a custom serializer, implement `Serialiser<T>` and register it:

```java
public class CustomSerializer extends Serialiser<MyClass> {
    // Implement serialize and deserialize methods
}

// Register the serializer
serde.register(MyClass.class, new CustomSerializer());
```

## Limitations

- **Limited Collection Support**: Standard collections (e.g., `ArrayDeque`, `EnumSet`) require custom serializers.
- **Type Registration Order**: Ensure all dependent types are registered before use.

## Exception Handling

Methods throw checked exceptions (`SerdeException`, `IOException`) that must be handled:

```java
try {
    byte[] bytes = serde.serialize(object);
    MyClass obj = serde.deserialize(bytes);
} catch (SerdeException | IOException e) {
    // Handle error
}
```

## License

Source code is primarily distributed under the terms of the MIT license. See LICENSE for details.
