# typeid

[![Build](https://github.com/softprops/typeid-java/actions/workflows/main.yml/badge.svg)](https://github.com/softprops/typeid-java/actions/workflows/main.yml) ![License Info](https://img.shields.io/github/license/softprops/typeid-java)

## A Java implementation of [TypeID](https://github.com/jetpack-io/typeid).

TypeIDs are a modern, **type-safe**, globally unique identifier based on the upcoming
UUIDv7 standard. They provide a ton of nice properties that make them a great choice
as the primary identifiers for your data in a database, APIs, and distributed systems.
Read more about TypeIDs in their [spec](https://github.com/jetpack-io/typeid).

## Installation

To add this library as a dependency in your app add the following to your build

### Gradle

```kotlin
implementation("me.lessis:typeid:0.0.2")
```

### Maven

```xml
<dependency>
    <groupId>me.lessis</groupId>
    <artifactId>typeid</artifactId>
    <version>0.0.2</version>
</dependency>
```

## Usage

This library provides both a statically typed and a dynamically typed version of TypeIDs.

The statically typed version lives under the `typed` package. It makes it possible for
the go compiler itself to enforce type safety.

To use it, first define your TypeID types:

```java
import typeid.TypeID

// generate type ids (prefix_7zzzzzzzzqf3b80018tr001cg3)
var id = new TypeID("prefix");

// parse typed ids from their string representation
var parsed = TypeID.fromString(id.toString());

// this is a fallible operation so the value returned
// is an Optional, present when values are valid, empty when not
parsed.ifPresent(
    id -> {
        System.out.println(
            "prefix %s suffix %s".formmated(
                id.prefix(), id.suffix();
            )
        );
    }
);

// get a java.util.UUID representation of a type id (ffffffff-fff7-78d6-8000-28d60000b203)
id.uuid();
```
