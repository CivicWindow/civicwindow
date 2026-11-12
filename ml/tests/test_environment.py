# Enforcement: pytest runs this file and Ruff checks it, both in the python-checks
# job of CI; the job first runs on the first pull request after the repository is
# published, and review is the check until then.
"""Checks that the Python environment is the one the project pins: Python 3.12.

Each test fails under the behavior it names: running on another interpreter, or
letting ``pyproject.toml`` admit another minor version (ADR-0005).
"""

import re
import sys
import tomllib
from pathlib import Path

PINNED_MINOR = (3, 12)
PYPROJECT = Path(__file__).resolve().parents[1] / "pyproject.toml"
# Specifiers that admit Python 3.12 and no other minor version.
SINGLE_MINOR_SPECIFIER = re.compile(r"==3\.12\.\*|~=3\.12\.\d+")


def test_interpreter_is_python_3_12() -> None:
    """Fail when the interpreter running the tests is not Python 3.12."""
    assert sys.version_info[:2] == PINNED_MINOR, (
        f"expected Python 3.12, found {sys.version_info.major}.{sys.version_info.minor}"
    )


def test_pyproject_allows_only_python_3_12() -> None:
    """Fail when ``requires-python`` admits a minor version other than 3.12."""
    with PYPROJECT.open("rb") as file:
        metadata = tomllib.load(file)
    specifier = metadata["project"]["requires-python"].replace(" ", "")
    assert SINGLE_MINOR_SPECIFIER.fullmatch(specifier), (
        f"requires-python must allow only Python 3.12, found {specifier!r}"
    )
