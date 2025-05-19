# Serde Serialization Framework

Serde is a flexible and extensible Java serialization library. It supports primitive types, arrays, objects, and custom classes with reflective property access.

## Features

- **Primitive Type Serializers:** Supports `int`, `long`, `float`, `double`, `boolean`, `char`, and their wrapper classes.
- **Array Serializers:** Handles serialization of primitive arrays (`int[]`, `long[]`, `byte[]`, etc.).
- **Object Serialization:** Provides reflective property-based object serialization, including support for nested objects.
- **Type Registry:** Manages class-to-ID mapping for efficient serialization/deserialization.
- **Serializer Registry:** Registers and retrieves serializers for specific classes, ensuring extensibility.
- **Custom Class Support:** Easily add custom serializers for user-defined classes.

## How to Use

### Initialization

Create a `Serde` instance and register default serializers:

```java
Serde serde = new Serde();
serde.registerDefault(); // Register basic primitive and array serializers
```

### Register Custom Classes

Register your class to enable serialization:

```java
serde.register(MyClass.class);
```

Or with a custom serializer:

```java
serde.register(MyClass.class, new MyClassSerializer());
```

### Serialization

Serialize an object to a byte array:

```java
MyClass obj = new MyClass(...);
byte[] data = serde.serialize(obj);
```

### Deserialization

Deserialize byte array back into an object:

```java
MyClass obj = serde.deserialize(data);
```

### Using Streams

Serialize to a stream:

```java
try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
     DataOutputStream dos = new DataOutputStream(baos)) {
    serde.serialize(dos, obj);
}
```

Deserialize from a stream:

```java
try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
     DataInputStream dis = new DataInputStream(bais)) {
    MyClass obj = serde.deserialize(dis);
}
```

## Extensibility

- **Custom Serializers:** Extend `Serialiser<T>` to define how specific classes are serialized/deserialized.
- **Reflective Property Access:** Uses Java reflection and method handles for efficient property access.
- **Array Serializers:** Implement support for new array types by extending existing array serializer classes.

## Examples

### Basic Serialization and Deserialization

```java
Serde serde = new Serde();
serde.registerDefault();

Data data = new Data(new boolean[] { true, false }, "test", 123);
byte[] serializedData = serde.serialize(data);

Data deserializedData = serde.deserialize(serializedData);
System.out.println(deserializedData);
```

### Custom Class Registration

```java
// Register your class
serde.register(YourCustomClass.class);
```

## License

Source code is primarily distributed under the terms of the MIT license. See LICENSE for details.
