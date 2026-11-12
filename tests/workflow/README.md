# Workflow acceptance checks

**Status:** Planned; no implementation or executable check exists here yet.

These planned checks prove role bootstrap, selective candidate selection, dependency integrity and release isolation after the relevant tooling exists.

**Who writes them:** the role that owns the workflow tooling a check proves. Each of the four areas has its own folder and README.

**How they are named:** files and test names follow the [naming rules](../../docs/testing/TEST_STRATEGY.md#naming-rules); a Python check is `test_<behavior>` in a `test_*.py` file.

**How they run:** there is no command yet; the command is added here with the first check. The [test strategy](../../docs/testing/TEST_STRATEGY.md#running-the-tests) lists every layer and its command.

**Enforcement:** review is the check until one is added under the "Adding a tool, package or dependency" rule of the coding standards.
