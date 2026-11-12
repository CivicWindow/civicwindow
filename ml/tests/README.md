# ML validation

**Status:** Planned, with one runner and one check: pytest is set up and [`test_environment.py`](test_environment.py) checks the interpreter. The test has not been run yet: the first run is the first run of the Python job in CI.

Keep taxonomy, preprocessing, model compatibility and evaluation checks here once real inputs exist. Test substitutes must be labeled.

## What exists

| File | What it checks | Fails when |
|---|---|---|
| [`test_environment.py`](test_environment.py) | `test_interpreter_is_python_3_12` | the interpreter running the tests is not Python 3.12 |
| [`test_environment.py`](test_environment.py) | `test_pyproject_allows_only_python_3_12` | `requires-python` in [`../pyproject.toml`](../pyproject.toml) admits another minor version |

A green test is evidence only for the property it would fail under a prohibited behavior ([validation strategy](../../docs/testing/TEST_STRATEGY.md)). Each test here names that behavior in its docstring. Every public test function has a docstring ([PEP 257](https://peps.python.org/pep-0257/)).

## Run the tests

Run from the `ml` folder, on Windows 10 or 11 with PowerShell 7 (the full set of commands, and the setup, are in [`../README.md`](../README.md)). The command is **not yet run**:

```powershell
uv run pytest
```

pytest finds the tests through `testpaths` in [`../pyproject.toml`](../pyproject.toml) and names files `test_*.py` ([pytest: conventions for test discovery](https://docs.pytest.org/en/stable/explanation/goodpractices.html)).

Enforcement: the `python-checks` job of CI runs pytest and Ruff; it first runs on the first pull request after the repository is published, and review is the check until then.
