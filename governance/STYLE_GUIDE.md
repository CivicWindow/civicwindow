# Shared style guide

Use plain names for product behavior, ownership and public interfaces. Keep headings, links and examples readable to all three roles. Use consistent terms for ticket, role acceptance, integration candidate and release. Mark illustrations as examples when they are not implemented or measured.

Formatting tools and language-specific naming rules are set by decision records, and the tool each record names checks them:
- **Kotlin and Android:** [ADR-0006](../docs/adr/0006-kotlin-android-style.md) adopts the Kotlin coding conventions and the Android Kotlin style guide, formatted and checked by ktlint and Android Lint, with the shared `.editorconfig`.
- **Python:** [ADR-0005](../docs/adr/0005-python-environment.md) adopts PEP 8 and PEP 257, formatted and checked by Ruff, with one shared `ruff.toml`; [ADR-0008](../docs/adr/0008-python-lock-tool.md) sets the environment.

The rules, the line lengths and the commands are in the [coding standards](CODING_STANDARDS.md). A style choice beyond those records is made only after the tool that checks it has run. This document does not make an untested Kotlin, Python or Android style choice on behalf of the team.

Enforcement: ktlint and Android Lint check Kotlin and Android code, and Ruff checks Python, in the CI jobs named in the coding standards. None has run yet; the first run is the first CI run after the repository is published, and until then review is the check.
