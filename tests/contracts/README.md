# Contract checks

**Status:** Planned; no implementation or executable check exists here yet.

Use producer and consumer fixtures, valid and invalid cases, version compatibility, and errors for each approved shared contract.

**Who writes them:** the role that owns a contract writes its producer check; each role that consumes the contract writes its consumer check.

**How they are named:** files and test names follow the [naming rules](../../docs/testing/TEST_STRATEGY.md#naming-rules). Shared inputs come from [`tests/fixtures/`](../fixtures/README.md).

**How they run:** there is no command yet; the command is added here with the first check. The [test strategy](../../docs/testing/TEST_STRATEGY.md#running-the-tests) lists every layer and its command.

**Enforcement:** review is the check until one is added under the "Adding a tool, package or dependency" rule of the coding standards.
