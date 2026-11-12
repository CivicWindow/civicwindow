# End-to-end checks

**Status:** Planned; no implementation or executable check exists here yet.

Exercise representative Android civic discovery flows, offline states, accessibility, evidence provenance and recovery on supported devices.

**Who writes them:** the role that owns the flow under test; a flow that crosses roles is written together by the roles it crosses.

**How they are named:** files and test names follow the [naming rules](../../docs/testing/TEST_STRATEGY.md#naming-rules); a test name is a sentence that says the flow and the result.

**How they run:** on a physical Android device, by hand; an emulator-only result does not establish device readiness. There is no command yet; the command is added here with the first check. The [test strategy](../../docs/testing/TEST_STRATEGY.md#running-the-tests) lists every layer and its command.

**Enforcement:** review is the check until one is added under the "Adding a tool, package or dependency" rule of the coding standards.
