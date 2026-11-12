# Shared test fixtures

**Status:** Planned; no implementation or executable check exists here yet.

Store small, permitted, versioned civic-record and evidence examples with provenance and expected behavior.

**Who writes them:** the role that owns the contract or the source an example illustrates. Examples for connectors live in `connectors/fixtures/` and are written by the role that owns the connector.

**How they are named:** a fixture file name says what it shows, and a valid case and an invalid case sit side by side. No standard covers this; it is the project's choice.

**How they run:** a fixture is an input, not a check, so it has no command. The checks that read it are listed in the [test strategy](../../docs/testing/TEST_STRATEGY.md#running-the-tests).

**Enforcement:** review is the check until one is added under the "Adding a tool, package or dependency" rule of the coding standards.
