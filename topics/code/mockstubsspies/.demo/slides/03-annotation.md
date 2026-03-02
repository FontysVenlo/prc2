---
title: "@Mock + @BeforeEach"
---

# `@Mock` + `@BeforeEach`

### `@Mock` — shorthand for `mock()`

```java
@Mock
Stock stock;
```
Managed by `MockitoExtension` — fresh mock for every test.

### `@BeforeEach` — shared setup

```java
@BeforeEach
void setup() {
    bar = new Minibar( stock );
}
```
Each test gets a **fresh** `Minibar` with a **fresh** mock.

### Verify the exact argument

```java
verify( stock ).take( "beer" );
```
Not just *any* string — exactly `"beer"`.
