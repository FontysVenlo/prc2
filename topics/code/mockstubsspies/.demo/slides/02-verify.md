---
title: mock() + verify()
---

# `mock()` + `verify()`

```java
Stock stock = mock( Stock.class );
```
Creates a fake `Stock` that does nothing — but **records every call**.

```java
verify( stock ).take( anyString() );
```
"Was `take()` called with **any** string?"

### The pattern: Arrange → Act → Assert

1. **Arrange** — create mock, build SUT
2. **Act** — call the method under test
3. **Assert** — verify the mock was called correctly
