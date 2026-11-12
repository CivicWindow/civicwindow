# Documentation standard

A maintained document states its purpose, implementation status, owner when assigned, applicable version/baseline, and related ticket or decision. Mark proposal, approved rule, implemented behavior and measured result distinctly. Update affected source, tests and documentation together.

Keep one authoritative definition for each schema or contract. Human-readable API docs link to it rather than defining a conflicting copy. Role documents link shared governance instead of maintaining separate style guides. Candidate and release records identify exact source and artifact revisions; they do not replace maintained design documents.

An empty file or generic template does not count as completed documentation. A planned folder may remain empty until a truthful document can be written.

## Comments in code

Every public function is commented, and each comment says what the function does and what it returns. A short comment is added wherever the reason for the code is not obvious.

- **Kotlin:** a KDoc comment on every public class and function ([ADR-0006](../docs/adr/0006-kotlin-android-style.md)). No tool checks this rule yet; review is the check until one is added under the "Adding a tool, package or dependency" rule in the [coding standards](CODING_STANDARDS.md#adding-a-tool-package-or-dependency).
- **Python:** a docstring on every public function, in the PEP 257 style ([ADR-0005](../docs/adr/0005-python-environment.md)). Ruff checks it with the pydocstyle rules selected in `ruff.toml`, through `ruff check` in the `python-checks` CI job. Proposed; check not yet run.

## The record of a model and of a dataset

The record of a model, and the record of a dataset, are checked against a published list of sections. No template file is kept; the record lists the sections below and fills each one, or says that it does not apply and why (no standard covers this; it is the project's choice).

- **A model** follows [Model Cards for Model Reporting](https://arxiv.org/abs/1810.03993): Model Details, Intended Use, Factors, Metrics, Evaluation Data, Training Data, Quantitative Analyses, Ethical Considerations, Caveats and Recommendations.
- **A dataset** follows [Datasheets for Datasets](https://arxiv.org/abs/1803.09010): Motivation, Composition, Collection Process, Preprocessing/cleaning/labeling, Uses, Distribution, Maintenance.

No tool checks that a record lists these sections; review is the check.

Enforcement: Ruff for the Python docstring rule, once the `python-checks` job has run; review is the check for every other rule in this file.
