# AI and ML development assets

**Status:** Planned, with a Python environment boilerplate: one pinned interpreter, one shared Ruff configuration and one environment test exist. There is no training, preprocessing, evaluation or conversion code yet. Every command below is **not yet run**: the first run is the first run of the Python job in CI.

This tree holds development-time taxonomy, dataset manifests and approved samples, preprocessing, training, evaluation, conversion, model manifests and their checks. The shipped inference boundary belongs in modules/ai/. Large datasets, model binaries and credentials need an approved artifact provider and hashes rather than casual Git commits.

## The Python environment

| What | Where | Value |
|---|---|---|
| Interpreter | [`.python-version`](../.python-version) and `requires-python` in [`pyproject.toml`](pyproject.toml) | Python 3.12 only ([ADR-0005](../docs/adr/0005-python-environment.md)) |
| Project description | [`pyproject.toml`](pyproject.toml) | the `[project]` table of [PEP 621](https://peps.python.org/pep-0621/) |
| Development tools | `[dependency-groups]` in [`pyproject.toml`](pyproject.toml) | Ruff 0.17.4 and pytest 9.2.0 |
| Lock file | `ml/uv.lock` | made by uv 0.13.7, never written by hand, checked into version control ([ADR-0008](../docs/adr/0008-python-lock-tool.md), Proposed) |
| Style | [`ruff.toml`](../ruff.toml) at the repository root | [PEP 8](https://peps.python.org/pep-0008/), [PEP 257](https://peps.python.org/pep-0257/), import order; 88 columns |
| Tests | [`tests/`](tests/README.md) | pytest |

No training library is installed yet. The first training ticket adds the libraries to `pyproject.toml`, and the lock file pins them then. Type checking is not adopted at this stage (ADR-0008 says when it is revisited).

The versions are proposed baselines, checked again at the first run of the Python job in CI.

## Commands

The commands are for Windows 10 and 11 with PowerShell 7 ([ADR-0002](../docs/adr/0002-build-toolchain.md)). Run them from the `ml` folder. They are **not yet run**.

1. Install uv 0.13.7 by following the [uv documentation](https://docs.astral.sh/uv/).
2. Go to the `ml` folder:

   ```powershell
   Set-Location ml
   ```

3. Create the environment from the lock file:

   ```powershell
   uv sync
   ```

   The first time, there is no `uv.lock` yet: `uv sync` makes it. Check the new file in and do not edit it by hand.

4. Check that the lock file is current. CI uses this form, and it fails when `pyproject.toml` changed without a new `uv.lock`:

   ```powershell
   uv sync --locked
   ```

5. Check style and format with Ruff:

   ```powershell
   uv run ruff check
   uv run ruff format --check
   ```

6. Run the tests with pytest:

   ```powershell
   uv run pytest
   ```

`scripts/` holds the commit-message check and its test, which use the standard library only. The Python job of CI covers that folder too, so both folders use the one configuration: it runs `uv run ruff check . ../scripts` and `uv run ruff format --check . ../scripts` in place of the two Ruff commands above, and `uv run pytest ../scripts/validation/tests` after the tests of the ML folder.

Running the tools through `uv run` is the project's choice; the same commands run as `ruff check`, `ruff format --check` and `pytest` inside the activated environment.

Whether every entry in `uv.lock` carries a hash is not confirmed by the uv documentation read for this setup. The first lock file is checked for it (ADR-0008).

Enforcement: Ruff and pytest run in the Python job of CI ([`python.yml`](../.github/workflows/python.yml)); review is the check until its first run.
