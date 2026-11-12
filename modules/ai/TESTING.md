# AI/ML testing expectations

**Status:** Test plan; no executable role build exists yet.

**Required concerns:** Taxonomy/label provenance, preprocessing, model interfaces, held-out evaluation, mobile latency/memory and real-device inference where applicable.

A future role manifest must name test commands, root/build prerequisites, fixtures and accepted dependency versions. Verify from a fresh checkout and empty cache. Missing inputs fail clearly. Record what was checked against real implementations and what used substitutes; substitute-only success cannot qualify a release.

The test layers, naming rules and commands are in the [test strategy](../../docs/testing/TEST_STRATEGY.md).
