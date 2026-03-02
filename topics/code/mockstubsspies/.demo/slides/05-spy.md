---
title: What is a spy?
---

# What is a spy?

A **spy** wraps a **real object**.

```java
SimpleStock realStock = new SimpleStock();
Stock spied = spy( realStock );
```

### Real methods execute!

Unlike a mock, the **actual code runs**.
But you can still **verify** interactions and **selectively stub**.

### Mock vs Stub vs Spy

| | Mock | Stub | Spy |
|---|------|------|-----|
| Real code runs? | No | No | **Yes** |
| Return values | Defaults | Configured | Real |
| Verify calls? | Yes | — | Yes |
| Created with | `mock()` | `when().thenReturn()` | `spy()` |

> A stub is not a separate type —
> you turn a mock into a stub with `when().thenReturn()`.
