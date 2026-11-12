# Validation scripts

**Status:** Proposed; one check exists, the commit-message check, and it has not run yet. The first run is the first run in CI after the repository is published.

The commit-message check is [`check_commit_messages.py`](check_commit_messages.py), with its tests in [`tests/`](tests/test_check_commit_messages.py). It reads the commits of a range and applies one of two rule groups, `--rule closing-keyword` (required) or `--rule format` (advisory), then the commit before the range and the last commit of the range. It uses the standard library only. The workflows README lists the command and the exit codes ([`.github/workflows/README.md`](../../.github/workflows/README.md)).

Future checks must validate real contracts, modules, schema versions, candidate identity and artifact integrity with explicit failure on missing inputs.

Enforcement: the `commit-messages` and `commit-format` jobs of CI run the commit-message check, and its test runs in the `python-checks` job. The jobs first run on the first pull request after the repository is published; review is the check until then.
