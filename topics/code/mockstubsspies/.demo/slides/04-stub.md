---
title: What is a stub?
---

# What is a stub?

A **stub** provides **canned answers** to method calls.

```java
when( stock.isAvailable("beer") ).thenReturn( true );
```

### Mock vs Stub

| | Mock | Stub |
|---|------|------|
| Purpose | Verify **interactions** | Provide **return values** |
| Key method | `verify()` | `when().thenReturn()` |
| Answers | Defaults (null, false, 0) | What you configure |

### Why do we need stubs?

When the SUT **depends on a return value** from the DOC.

> "If the minibar checks availability before serving,
> we need to stub `isAvailable()` to return `true`."

### Important Mockito default

If you do **not** stub a method on a mock, Mockito might need to return a default value. For example, a `boolean` method on a mock returns `false` by default.

So `stock.isAvailable("beer")` is `false` unless you explicitly configure it.
