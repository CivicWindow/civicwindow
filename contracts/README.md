# Shared code contracts

**Status:** No Kotlin interfaces are implemented. The build project exists (`build.gradle.kts`, no sources by design); no build has run yet, and the first CI run after publication checks it. Every role will receive this directory.

A contract must be reviewed by its producer and consumers before implementation. Define input and output behavior, nullability, errors, versioning, evidence provenance, and offline behavior. Tie the implementation to approved schemas and test it with producer and consumer fixtures. Focused role tests can use explicit substitutes; integration must use selected real implementations.

See [schema governance](../governance/SCHEMA_VERSIONING.md) and [interface planning](../docs/api-contracts/README.md).
