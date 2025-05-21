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
serde.references(true); // For tracking object references
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

## Serialization Speed

On average, Serde is approximately three times slower than [Kryo](https://github.com/EsotericSoftware/kryo).
All benchmarks are available in the `SerdeBenchmark` and `KryoBenchmark` classes. Here are the full benchmark results:

<details>
<summary>Results for Serde</summary>
<pre>
Benchmark                           (compact)  (references)  (type)  Mode  Cnt    Score    Error  Units
SerdeBenchmark.serializeDeserialize      false         false   small  avgt    5    0,540 ±  0,049  ms/op
SerdeBenchmark.serializeDeserialize      false         false  medium  avgt    5    5,099 ±  0,077  ms/op
SerdeBenchmark.serializeDeserialize      false         false   large  avgt    5   53,615 ±  1,679  ms/op
SerdeBenchmark.serializeDeserialize      false          true   small  avgt    5    0,956 ±  0,032  ms/op
SerdeBenchmark.serializeDeserialize      false          true  medium  avgt    5   11,127 ±  0,930  ms/op
SerdeBenchmark.serializeDeserialize      false          true   large  avgt    5  146,923 ± 15,447  ms/op
SerdeBenchmark.serializeDeserialize       true         false   small  avgt    5    0,925 ±  0,014  ms/op
SerdeBenchmark.serializeDeserialize       true         false  medium  avgt    5    9,130 ±  0,053  ms/op
SerdeBenchmark.serializeDeserialize       true         false   large  avgt    5   93,193 ±  5,065  ms/op
SerdeBenchmark.serializeDeserialize       true          true   small  avgt    5    1,412 ±  0,086  ms/op
SerdeBenchmark.serializeDeserialize       true          true  medium  avgt    5   15,556 ±  1,400  ms/op
SerdeBenchmark.serializeDeserialize       true          true   large  avgt    5  204,313 ± 14,349  ms/op
SerdeBenchmark.serializeOnly             false         false   small  avgt    5    0,257 ±  0,016  ms/op
SerdeBenchmark.serializeOnly             false         false  medium  avgt    5    2,707 ±  0,183  ms/op
SerdeBenchmark.serializeOnly             false         false   large  avgt    5   35,299 ±  4,097  ms/op
SerdeBenchmark.serializeOnly             false          true   small  avgt    5    0,550 ±  0,070  ms/op
SerdeBenchmark.serializeOnly             false          true  medium  avgt    5    5,578 ±  3,654  ms/op
SerdeBenchmark.serializeOnly             false          true   large  avgt    5   72,958 ± 36,182  ms/op
SerdeBenchmark.serializeOnly              true         false   small  avgt    5    0,557 ±  0,100  ms/op
SerdeBenchmark.serializeOnly              true         false  medium  avgt    5    5,421 ±  1,725  ms/op
SerdeBenchmark.serializeOnly              true         false   large  avgt    5   47,972 ±  1,858  ms/op
SerdeBenchmark.serializeOnly              true          true   small  avgt    5    0,651 ±  0,146  ms/op
SerdeBenchmark.serializeOnly              true          true  medium  avgt    5    6,351 ±  0,196  ms/op
SerdeBenchmark.serializeOnly              true          true   large  avgt    5  114,660 ± 15,368  ms/op
</pre>
</details>

<details>
<summary>Result for Kryo</summary>
<pre>
Benchmark                          (optimized)  (references)  (type)  Mode  Cnt   Score    Error  Units
KryoBenchmark.serializeDeserialize        false         false   small  avgt    5   0,172 ±  0,009  ms/op
KryoBenchmark.serializeDeserialize        false         false  medium  avgt    5   2,174 ±  0,694  ms/op
KryoBenchmark.serializeDeserialize        false         false   large  avgt    5  21,164 ± 15,367  ms/op
KryoBenchmark.serializeDeserialize        false          true   small  avgt    5   0,336 ±  0,027  ms/op
KryoBenchmark.serializeDeserialize        false          true  medium  avgt    5   4,335 ±  0,365  ms/op
KryoBenchmark.serializeDeserialize        false          true   large  avgt    5  82,927 ± 80,186  ms/op
KryoBenchmark.serializeDeserialize         true         false   small  avgt    5   0,294 ±  0,031  ms/op
KryoBenchmark.serializeDeserialize         true         false  medium  avgt    5   2,788 ±  0,541  ms/op
KryoBenchmark.serializeDeserialize         true         false   large  avgt    5  22,572 ±  1,055  ms/op
KryoBenchmark.serializeDeserialize         true          true   small  avgt    5   0,372 ±  0,048  ms/op
KryoBenchmark.serializeDeserialize         true          true  medium  avgt    5   4,485 ±  0,196  ms/op
KryoBenchmark.serializeDeserialize         true          true   large  avgt    5  65,395 ±  6,426  ms/op
KryoBenchmark.serializeOnly               false         false   small  avgt    5   0,081 ±  0,003  ms/op
KryoBenchmark.serializeOnly               false         false  medium  avgt    5   0,912 ±  0,109  ms/op
KryoBenchmark.serializeOnly               false         false   large  avgt    5   8,924 ±  0,649  ms/op
KryoBenchmark.serializeOnly               false          true   small  avgt    5   0,155 ±  0,003  ms/op
KryoBenchmark.serializeOnly               false          true  medium  avgt    5   2,569 ±  0,201  ms/op
KryoBenchmark.serializeOnly               false          true   large  avgt    5  36,747 ±  3,432  ms/op
KryoBenchmark.serializeOnly                true         false   small  avgt    5   0,102 ±  0,005  ms/op
KryoBenchmark.serializeOnly                true         false  medium  avgt    5   0,998 ±  0,056  ms/op
KryoBenchmark.serializeOnly                true         false   large  avgt    5  10,874 ±  0,930  ms/op
KryoBenchmark.serializeOnly                true          true   small  avgt    5   0,219 ±  0,032  ms/op
KryoBenchmark.serializeOnly                true          true  medium  avgt    5   3,718 ±  0,855  ms/op
KryoBenchmark.serializeOnly                true          true   large  avgt    5  55,687 ±  7,858  ms/op
</pre>
</details>

## License

Source code is primarily distributed under the terms of the MIT license. See LICENSE for details.
