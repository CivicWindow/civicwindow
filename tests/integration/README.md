# Integration checks

**Status:** Planned; no implementation or executable check exists here yet.

Compose exact real selected implementations and dependencies from one candidate manifest. A mock-only pass cannot approve integration.

**Who writes them:** the roles whose modules the check composes, together.

**How they are named:** files and test names follow the [naming rules](../../docs/testing/TEST_STRATEGY.md#naming-rules). A check that uses a controlled substitute says so in its name or a comment, and it cannot approve integration.

**How they run:** there is no command yet; the command is added here with the first check. The [test strategy](../../docs/testing/TEST_STRATEGY.md#running-the-tests) lists every layer and its command.

**Enforcement:** review is the check until one is added under the "Adding a tool, package or dependency" rule of the coding standards.
