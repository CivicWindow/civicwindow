# Controlled test substitutes

**Status:** Planned; no implementation or executable check exists here yet.

Store versioned substitutes and agreed outputs for focused role tests. Label them clearly and prevent their use as release evidence.

**Who writes them:** the role that owns the interface a substitute stands in for.

**How they are named:** a substitute says in its file name or a comment that it is a substitute, and names the thing it replaces. Tests that use one follow the [naming rules](../../docs/testing/TEST_STRATEGY.md#naming-rules). No standard covers the labelling; it is the project's choice.

**How they run:** a substitute is an input, not a check, so it has no command. Success that rests only on substitutes cannot qualify a release (see the [definition of done](../../governance/DEFINITION_OF_DONE.md)).

**Enforcement:** review is the check until one is added under the "Adding a tool, package or dependency" rule of the coding standards.
