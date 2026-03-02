---
title: Recap
---

# Recap — Mock, Stub & Spy

| Tool | Purpose |
|------|---------|
| `mock()` / `@Mock` | Create fake objects that record calls |
| `verify()` | Check interactions happened |
| `when().thenReturn()` | **Stub** — provide canned answers |
| `spy()` | Wrap a real object — real methods run |

### Key insight

- **Mock** → verify the SUT *calls the right methods*
- **Stub** → control what the DOC *returns*
- **Spy** → test with a *real* DOC, but still verify

### What's next?

Practice with mocks, stubs, and spies in the assignment!
