# Performance checks

**Status:** Planned; no implementation or executable check exists here yet.

Define supported devices and measured budgets for app startup, retrieval, inference, storage, model footprint and battery before release.

**Who writes them:** the role that owns the behavior being measured; the budgets are agreed across roles before a check is written.

**How they are named:** files and test names follow the [naming rules](../../docs/testing/TEST_STRATEGY.md#naming-rules); a name says what is measured and the budget.

**How they run:** on a physical Android device, by hand. There is no command yet; the command is added here with the first check. The [test strategy](../../docs/testing/TEST_STRATEGY.md#running-the-tests) lists every layer and its command.

**Enforcement:** review is the check until one is added under the "Adding a tool, package or dependency" rule of the coding standards.
