# Data Integration/Platform testing expectations

**Status:** Test plan; no executable role build exists yet.

**Required concerns:** Fixture provenance, malformed records, duplicate/version handling, source availability, rate limits, normalization and persistence migrations.

A future role manifest must name test commands, root/build prerequisites, fixtures and accepted dependency versions. Verify from a fresh checkout and empty cache. Missing inputs fail clearly. Record what was checked against real implementations and what used substitutes; substitute-only success cannot qualify a release.

The test layers, naming rules and commands are in the [test strategy](../../docs/testing/TEST_STRATEGY.md).
