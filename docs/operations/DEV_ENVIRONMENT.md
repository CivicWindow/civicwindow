# Development environment

**Status:** Proposed. This guide sets up a Windows 10 or 11 machine with PowerShell 7 ([ADR-0002](../adr/0002-build-toolchain.md)) so that one lead can build, test and check their own area alone. Every version is a proposed baseline, checked again at the first build. Every command is **not yet run**: the first run is the first build after the repository is published. A value marked "open: confirmed at the first build" is not confirmed by the documentation read for this guide, and the person who sets up the first machine settles it and records it here.

The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md) (section 5) says that setup records the exact versions of the tools, so any member can reproduce the build. This guide does that in the table under [Check your versions](#check-your-versions).

The three roles are written exactly `android`, `ai-ml` and `data-platform` in every table and command. With `-Pcivicwindow.role=<role>` the build includes only that role's projects; without it, the build includes every project ([role onboarding](ROLE_ONBOARDING.md), [role configurations](../../role-configs/README.md)).

## What to install

Install only what your role's table lists. A tool is in a role's table only if that role's build, checks or tests need it. Install the "Every member" tools first, then the tools of your role.

Run the commands in PowerShell 7. The version column is the exact version to install; none of these installs has been run yet.

### Every member

| Tool | Exact version | Official source | Why the project needs it | Check and what it prints | Roles |
|---|---|---|---|---|---|
| Git for Windows | 2.56.1 (proposed baseline, checked again at the first build) | [git-scm.com, "Download for Windows"](https://git-scm.com/install/windows) | The Software Design Document (section 5) lists Git among the recorded tools; the [branching policy](../../governance/BRANCHING_POLICY.md) and the [worktree guide](WORKTREE_GUIDE.md) use it | `git --version` prints the installed version; the exact text is not confirmed by the saved documentation | android, ai-ml, data-platform |
| Eclipse Temurin JDK 17 (Windows x64 MSI) | 17.0.21+9 (proposed baseline, checked again at the first build) | [Eclipse Adoptium, Windows installation](https://adoptium.net/installation/windows/); the version is from [Adoptium's release API](https://api.adoptium.net/v3/assets/latest/17/hotspot?architecture=x64&image_type=jdk&os=windows) | [ADR-0002](../adr/0002-build-toolchain.md) (JDK 17); the Android Gradle Plugin 9.3 needs JDK 17 as its minimum and default ([release notes](https://developer.android.com/build/releases/agp-9-3-0-release-notes)); the `civicwindow.base` plugin sets a JDK 17 toolchain; CI uses Temurin | `./gradlew --version` reports Gradle 9.6.0 and JDK 17 (not yet run). `java -version` prints the installed JDK version; open: confirmed at the first build | android, ai-ml, data-platform |
| PowerShell 7 (the 7.6 LTS line) | 7.6 or later (7.6.6 is the current LTS); installed with `winget`. Which version `winget` installs is open: confirmed at the first build, and 7.7 is accepted (proposed baseline, checked again at the first build) | Microsoft Learn, ["PowerShell support lifecycle"](https://learn.microsoft.com/en-us/powershell/scripting/install/powershell-support-lifecycle) and ["Install PowerShell on Windows"](https://learn.microsoft.com/en-us/powershell/scripting/install/installing-powershell-on-windows) | [ADR-0002](../adr/0002-build-toolchain.md) (Windows 10 and 11 with PowerShell 7); every command in the project's guides | `$PSVersionTable` prints a table that includes PSVersion | android, ai-ml, data-platform |

Install steps (not yet run):

1. Git: run the installer `Git-2.56.1-64-bit.exe` from the Git download page.
2. JDK: run `OpenJDK17U-jdk_x64_windows_hotspot_17.0.21_9.msi` (install for all users, x64).
3. PowerShell 7:

   ```powershell
   winget install --id Microsoft.PowerShell --source winget
   ```

   The command has no version in it. Open: confirmed at the first build.
4. Set the `JAVA_HOME` environment variable to the folder of the Temurin 17 JDK, so that a terminal build uses it. Gradle run in a terminal uses the JDK that `JAVA_HOME` names ([Java versions in Android builds](https://developer.android.com/build/jdks)). Open: the Windows command is confirmed at the first build.

The Microsoft Build of OpenJDK 17 is also an LTS JDK 17 ([download page](https://learn.microsoft.com/en-us/java/openjdk/download)). It is not the project's choice, because CI uses Temurin and a terminal build then matches CI.

### The android role

| Tool | Exact version | Official source | Why the project needs it | Check and what it prints | Roles |
|---|---|---|---|---|---|
| Android Studio | Rabbit 2 (2026.2.2), or any version whose Android Gradle Plugin range reaches 9.3, from Quail 2 (2026.1.2) on (proposed baseline, checked again at the first build) | Android Developers, ["Android Studio releases"](https://developer.android.com/studio/releases) and ["Download Android Studio"](https://developer.android.com/studio) | [ADR-0002](../adr/0002-build-toolchain.md) (a current stable Android Studio that supports the Android Gradle Plugin 9.3) | The "About" window shows the version; the printed text is not confirmed by the saved documentation | android |
| Android SDK Platform, API level 37 | API level 37, Android 17 (proposed baseline, checked again at the first build) | Android Developers, ["sdkmanager"](https://developer.android.com/tools/sdkmanager) and ["uses-sdk element"](https://developer.android.com/guide/topics/manifest/uses-sdk-element) | [ADR-0003](../adr/0003-android-sdk-levels.md) (compile level 37); the build files | The SDK Manager lists the platform as installed (open: confirmed at the first build). The package name for `sdkmanager` is open: confirmed at the first build | android |
| Android SDK Build-Tools | 36.0.0 (proposed baseline, checked again at the first build) | Android Developers, ["Android Gradle plugin 9.3 release notes"](https://developer.android.com/build/releases/agp-9-3-0-release-notes) | The Android Gradle Plugin 9.3's minimum and default build tools | The SDK Manager lists build-tools 36.0.0 as installed (open: confirmed at the first build). The latest stable build-tools release is open | android |
| Android SDK Platform-Tools (`adb`) | 37.0.1 (proposed baseline, checked again at the first build) | Android Developers, ["SDK Platform Tools release notes"](https://developer.android.com/tools/releases/platform-tools) | Physical-device testing ([ADR-0003](../adr/0003-android-sdk-levels.md); the Software Design Document's device rule) | `adb version`; open: confirmed at the first build | android |
| Physical-device setup (steps, not a tool) | developer options, USB debugging, the Windows USB driver, and optional wireless debugging; the licence step `sdkmanager --licenses` (proposed baseline, checked again at the first build) | Android Developers, ["Configure on-device developer options"](https://developer.android.com/studio/debug/dev-options), ["Run apps on a hardware device"](https://developer.android.com/studio/run/device) and ["sdkmanager"](https://developer.android.com/tools/sdkmanager) | Device tests are required ([ADR-0009](../adr/0009-test-libraries-and-coverage.md); the Software Design Document's device rule) | The device appears in Android Studio's device list; the printed text is not confirmed by the saved documentation | android |

Android Studio runs on its own bundled JetBrains Runtime. Set Android Studio's Gradle JDK setting to the same Temurin 17 JDK as `JAVA_HOME`, so that a build started from Android Studio and a build started in a terminal use the same JDK ([Java versions in Android builds](https://developer.android.com/build/jdks)). Open: the menu path of the setting is confirmed at the first build.

The main route to the SDK Platform and the Build-Tools is Android Studio's SDK Manager. `sdkmanager` is the command-line route for a setup without Android Studio; its page marks it deprecated in favour of the Android CLI's `android sdk` command, whose syntax is open: confirmed at the first build. To accept the SDK licences in a command-line-only setup (not yet run):

```powershell
sdkmanager --licenses
```

### The data-platform role

| Tool | Exact version | Official source | Why the project needs it | Check and what it prints | Roles |
|---|---|---|---|---|---|
| uv | 0.13.7 (proposed baseline, checked again at the first build) | Astral uv documentation, ["Installation"](https://docs.astral.sh/uv/getting-started/installation/) | [ADR-0008](../adr/0008-python-lock-tool.md) (the lock tool); [`ml/README.md`](../../ml/README.md) | `uv --version`; open: confirmed at the first build | data-platform, ai-ml |
| Python 3.12, installed through uv | 3.12 (`uv python install 3.12`); the patch release uv installs is not stated, open: confirmed at the first build (proposed baseline, checked again at the first build) | Astral uv documentation, ["Installing Python"](https://docs.astral.sh/uv/guides/install-python/) | [ADR-0005](../adr/0005-python-environment.md) (`.python-version` and `requires-python`) | `uv run pytest` from the `ml` folder: the environment test `test_interpreter_is_python_3_12` fails on another interpreter (not yet run) | data-platform, ai-ml |

Install uv, then Python (not yet run):

```powershell
powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/0.13.7/install.ps1 | iex"
uv python install 3.12
```

Python is installed through uv because python.org's current 3.12 release, 3.12.16, has no Windows installer, and the newest one with an installer on the saved pages is 3.12.10 (whether 3.12.11 to 3.12.15 had one is open: confirmed at the first build), and 3.12 is in its security-only period until 2028-10 ([Python releases for Windows](https://www.python.org/downloads/windows/), [status of Python versions](https://devguide.python.org/versions/)). `.python-version` stays the pin. A member who prefers an installer can use the [3.12.10 installer](https://www.python.org/downloads/release/python-31210/); the latest 3.12 release, [3.12.16](https://www.python.org/downloads/release/python-31216/), is source only. Whether 3.12.11 to 3.12.15 had Windows installers is open.

Create the Python environment from the `ml` folder (not yet run). The first time, `uv sync` makes `ml/uv.lock`; check the new file in and do not edit it by hand. After that, `uv sync --locked` fails when `pyproject.toml` changed without a new lock file ([`ml/README.md`](../../ml/README.md)):

```powershell
Set-Location ml
uv sync
uv sync --locked
```

### The ai-ml role

| Tool | Exact version | Official source | Why the project needs it | Check and what it prints | Roles |
|---|---|---|---|---|---|
| The data-platform tools above: uv and Python 3.12 | as listed in the table above (proposed baseline, checked again at the first build) | as listed in the table above | [ADR-0007](../adr/0007-module-and-build-structure.md) (the `ai-ml` role build) | as listed in the table above | ai-ml |
| The `ml/` dependencies from the lock file | the versions in `ml/uv.lock`, made by the first `uv sync` (proposed baseline, checked again at the first build) | the lock file, `ml/uv.lock` | [ADR-0008](../adr/0008-python-lock-tool.md) | `uv sync --locked` in the `ml` folder (not yet run) | ai-ml |

## Check your versions

Write the exact installed versions in this table on your machine, in your own notes or in the ticket that sets you up. Use only the rows of your role. The expected versions are proposed baselines, checked again at the first build.

| Tool | Expected version | Installed version (fill in) | Date (fill in) |
|---|---|---|---|
| Git for Windows | 2.56.1 | | |
| Eclipse Temurin JDK 17 | 17.0.21+9 | | |
| PowerShell 7 | 7.6 or later | | |
| Android Studio (android) | Rabbit 2 (2026.2.2), or from Quail 2 (2026.1.2) on | | |
| Android SDK Platform (android) | API level 37 | | |
| Android SDK Build-Tools (android) | 36.0.0 | | |
| Android SDK Platform-Tools (android) | 37.0.1 | | |
| uv (data-platform, ai-ml) | 0.13.7 | | |
| Python (data-platform, ai-ml) | 3.12 | | |
| Gradle, only for the first run | 9.6.0 | | |
| An unavailable artifact or tool | none expected | | |

Record also the operating system version and any tool or artifact that is unavailable to you ([role onboarding](ROLE_ONBOARDING.md)).

## First run: the Gradle wrapper

The wrapper scripts and the wrapper jar are made by a tool, checked in and never written by hand. The repository does not have them until someone runs the first-run command once, from the repository root, with a Gradle installed on that machine. Afterwards everyone uses `gradlew` and `gradlew.bat`, and the installed Gradle is no longer needed. If `gradlew` is not in the repository when you start, run the first-run command yourself: nobody else's action is needed ([Gradle README](../../gradle/README.md)).

| Tool | Exact version | Official source | Why the project needs it | Check and what it prints | Roles |
|---|---|---|---|---|---|
| Gradle (first run only) | 9.6.0 (proposed baseline, checked again at the first build) | Gradle Docs, ["Installation"](https://docs.gradle.org/current/userguide/installation.html) and ["Gradle Wrapper"](https://docs.gradle.org/9.6.0/userguide/gradle_wrapper.html); Gradle ["Release checksums"](https://gradle.org/release-checksums/) | [ADR-0002](../adr/0002-build-toolchain.md); the [Gradle README](../../gradle/README.md) and `gradle/wrapper/gradle-wrapper.properties` (the wrapper and its checksum) | `gradle -v` prints the Gradle version; afterwards `./gradlew --version` | whoever runs the first-run command, once; everyone else uses `gradlew` |

One-time Gradle install (not yet run):

1. Download the Gradle 9.6.0 binary-only zip (`gradle-9.6.0-bin.zip`) from the [Gradle releases page](https://gradle.org/releases/). Its SHA256 checksum is `bbaeb2fef8710818cf0e261201dab964c572f92b942812df0c3620d62a529a01` (the [release checksums](https://gradle.org/release-checksums/) page lists it). The installation page's examples show a newer Gradle; use 9.6.0.
2. Unzip it into a folder of your choice and add its `bin` folder to `Path`. Open: the folder and the `Path` step are confirmed at the first build.
3. Check the install in a new PowerShell 7 window. Gradle needs JDK 17 or higher:

   ```powershell
   gradle -v
   ```

Then, from the repository root, make the wrapper files (not yet run):

```powershell
gradle wrapper --gradle-version 9.6.0 --distribution-type bin --gradle-distribution-sha256-sum bbaeb2fef8710818cf0e261201dab964c572f92b942812df0c3620d62a529a01
```

It makes `gradlew`, `gradlew.bat` and `gradle/wrapper/gradle-wrapper.jar`, and the settings in `gradle/wrapper/gradle-wrapper.properties` must equal the same values.

Then write the dependency verification metadata and the lock files. Both are made by Gradle and checked in (not yet run):

```powershell
gradle --write-verification-metadata sha256 help
```

The verification command comes from Gradle's [dependency verification documentation](https://docs.gradle.org/9.0.0/userguide/dependency_verification.html) for Gradle 9.0.0; open: the form for 9.6.0 is confirmed at the first build. The command that writes the lock files uses Gradle's write-locks option; open: the exact command is confirmed at the first build.

Finally, check the wrapper (not yet run):

```powershell
./gradlew --version
```

It should report Gradle 9.6.0 and JDK 17.

## First build, test and check commands

Run the Gradle commands from the repository root and the Python commands from the `ml` folder. They are the commands that CI runs, so a local run matches CI ([CI workflows](../../.github/workflows/README.md)). All are **not yet run**.

First build and unit tests:

```powershell
./gradlew assembleDebug testDebugUnitTest
```

The checks of the `android` role, one command for each CI job (`android-build`, `android-unit-tests`, `android-static-analysis`). `:build-logic:test` is open: confirmed at the first build, and so are the task names `testDebugUnitTest`, `createDebugUnitTestCoverageReport`, `ktlintCheck` and `lintDebug`:

```powershell
./gradlew assembleDebug assembleDebugAndroidTest
./gradlew testDebugUnitTest :build-logic:test createDebugUnitTestCoverageReport
./gradlew ktlintCheck lintDebug
```

`assembleDebugAndroidTest` compiles the device tests; they run by hand on a physical device (see [Physical device setup](#physical-device-setup)). ktlint checks the format of Kotlin files and Android Lint checks Android code. detekt is added later, as the [build-logic README](../../build-logic/README.md) says; no command here runs it.

The checks of the `ai-ml` role, which owns `ml/` (the Python job, `python-checks`, which also checks the shared `scripts/` folder), from the `ml` folder:

```powershell
uv sync --locked
uv run ruff check . ../scripts
uv run ruff format --check . ../scripts
uv run pytest --cov --cov-branch --cov-report=term-missing
uv run pytest ../scripts/validation/tests
```

Ruff checks style and format and pytest runs the tests. Open: confirmed at the first build: the coverage options of the third `pytest` command and the command `uv run pytest ../scripts/validation/tests`. There is no coverage threshold.

The checks every member can run before a pull request (the jobs `commit-messages`, `commit-format` and `secret-scan`), from the repository root. `<base>` is the commit before the range and `<head>` is the last commit of the range:

```powershell
python scripts/validation/check_commit_messages.py --rule closing-keyword <base> <head>
python scripts/validation/check_commit_messages.py --rule format <base> <head>
gitleaks git --config .gitleaks.toml --redact --no-banner .
gitleaks dir --config .gitleaks.toml --redact --no-banner .
```

The first two run the commit-message script, which uses only the standard library. The last two run the Gitleaks command-line program, installed for your system at the release the [CI workflow notes](../../.github/workflows/README.md) name; how to install it on Windows is open: confirmed at the first build.

## Physical device setup

Device tests are required: at least one designated tester needs a physical Android device, and an emulator-only result does not establish device readiness ([Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md)). This section is part of the `android` role's setup. The steps are not yet run.

1. Turn on developer options: on the device, tap the Build Number option seven times until you see the message "You are now a developer!" ([Configure on-device developer options](https://developer.android.com/studio/debug/dev-options)).
2. Turn on USB debugging: open the Settings app, select Developer options, and turn on USB debugging if it is offered ([Run apps on a hardware device](https://developer.android.com/studio/run/device)).
3. On Windows, install a USB driver for ADB if your device needs one. The device page above describes it; the page for each manufacturer's driver is not referenced here, and finding it is open: confirmed at the first build.
4. Optional wireless debugging: Android 11 and higher can deploy and debug over Wi-Fi once the device is paired with the computer using a pairing code ([Run apps on a hardware device](https://developer.android.com/studio/run/device)).
5. Check that the device appears in Android Studio's device list. The printed text is not confirmed by the saved documentation.

## Local files the repository never holds

Files such as `local.properties`, keystores (`*.jks`, `*.keystore`, `*.p12`), key and certificate files, `.env` files other than `.env.example`, API keys, tokens and passwords stay on your machine and are never committed. The repository's ignore rules cover these names. The rules, and what to do if a secret is committed or shown, are in the [security decisions](../security/README.md).

## Later, per role

These tools do not affect the initial setup. Each is the owning role's first action item, and each is added under the "Adding a tool, package or dependency" rule of [`governance/CODING_STANDARDS.md`](../../governance/CODING_STANDARDS.md#adding-a-tool-package-or-dependency).

| Item | Role | The record that decides it |
|---|---|---|
| The on-device engine | `ai-ml` | [ADR-0004](../adr/0004-inference-runtime.md), by its device test |
| Room and KSP | the role named in the first feature ticket that needs them | that ticket; [ownership and coverage](OWNERSHIP_AND_COVERAGE.md) |
| Dependency injection | the role named in the ticket, under the same rule | that ticket; [ownership and coverage](OWNERSHIP_AND_COVERAGE.md) |
| The Open States key | `data-platform` | kept as the [security decisions](../security/README.md) say, and never in the repository |
| The launcher icon | `android` | a product asset |

## Other systems

The project documents name Windows 10 and 11 with PowerShell 7 for development. Other systems are added only if a member needs one, and then this guide gets a section for it.

Enforcement: The commands run in the clean-clone check before a release; the first lead's setup is the test.
