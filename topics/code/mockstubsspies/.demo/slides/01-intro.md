---
title: Why do we need mocks?
---

# Why do we need mocks?

A minibar serves drinks from stock.

**How do we test that `serve()` actually takes from stock?**

- We can't run a real warehouse in a unit test
- We need a **stand-in**: a **mock**

> A mock is a fake object that **records calls**.
> We **verify** afterwards that the right calls were made.
