# Security and privacy decisions

**Status:** Threat model, data retention policy, signing process, and security checks require review before release.

CivicWindow should minimize personal information, keep secrets and signing material out of source control, validate external civic records and model packages, and show source provenance. Record decisions and evidence here as features are designed. See [security governance](../../governance/SECURITY_POLICY.md).

**No cleartext traffic.** The app permits no cleartext (unencrypted) network traffic: Android's network security configuration sets this in the app's [`network_security_config.xml`](../../app/src/main/res/xml/network_security_config.xml), and an exception for a domain needs a decision record.

## Signing keys and secrets

**Status:** Proposed. The signing service and the secret store are not selected.

This follows the Android app signing documentation and the OWASP Secrets Management Cheat Sheet. The rule is the same for every module, branch and contributor.

**Never committed** (to a branch, a ticket, a pull request, a fixture, a log or a generated report): keystores (`*.jks`, `*.keystore`, `*.p12`), key and certificate files (`*.pem`, `*.key`), `keystore.properties`, `signing.properties`, `secrets.properties`, `local.properties`, `.env` files other than `.env.example`, API keys, tokens and passwords. The [ignore rules](../../.gitignore) cover these names.

**Where they live**
- **Debug signing:** each contributor uses the debug key their own Android tooling creates on their own machine. It is not shared and not copied into the repository.
- **Release signing:** the release key is held by the release coordinator ([ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md)) outside the repository and outside any synced folder, in encrypted storage, with a second copy held by one other lead. No standard covers this arrangement; it is the project's choice. The storage service is chosen before the first release.
- **Local settings and keys:** each contributor keeps them in files the ignore rules cover, or in environment variables. The repository holds only examples with no values, such as `.env.example`.
- **API keys for data sources:** each contributor registers and uses their own key for development, kept as local settings. How a key reaches the shipped app, which has no server, is an open decision.

**How a contributor gets what they need**
- Debug builds need nothing from anyone.
- A signed release build is made only by the release coordinator. A contributor who needs one asks through a private channel.
- Keys and tokens are never sent in email, chat, tickets or pull requests.

**If a secret is committed or shown:** treat it as exposed. Revoke or rotate it first, because removing it from history does not undo the exposure. Then tell the release coordinator, then remove it from the repository history.

Enforcement: the `secret-scan` job scans the commit history and the working tree in continuous integration, and a scan is also made before publication. The job first runs on the first pull request after the repository is published; until then, review is the check. The cleartext rule is set in the app's `network_security_config.xml`, and review is the check on a domain exception.
