# ADR-0004: The on-device inference runtime

- Status: Accepted
- Date: 2026-11-10
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: AI/ML lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

The application runs its models on the phone, and needs one inference runtime. The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 2.1.4, leaves the choice to testing:

```text
AI inference | LiteRT or ONNX Runtime Mobile, selected through compatibility and performance testing
```

Section 5 names the candidates and the rule of one:

```text
LiteRT 2.3 line, or ONNX Runtime Mobile (`onnxruntime-android`) 1.27.0 if testing favors it; the team would adopt one runtime, not both, unless testing shows a need
```

Section 2.1.2 sets the budgets as targets, not measurements:

```text
the following are targets to be tested, not measurements.
```

```text
the application would stay within about 1 GB of memory while one model is loaded, and would load models one at a time.
```

## Considered options

1. **LiteRT, 2.3 line.**
2. **ONNX Runtime Mobile, 1.27.0.**

No other runtime is considered. The versions are the Design Doc's baseline, proposed as the recent stable release of November 2026 and checked again when the runtime is added to the version catalog. No standard covers the choice of a runtime.

## Decision outcome

This record decides the method, not the runtime. The project adopts exactly one runtime, chosen by a test run on a physical device against the three candidate models of the Design Doc. The record names no default runtime.

The criteria, in the order the Design Doc's budgets imply:

1. The model converts and loads.
2. The output matches the reference output within a tolerance stated in the test plan.
3. Latency and peak memory are within the section 2.1.2 targets.
4. Package size is within budget.
5. The minimum SDK level ([ADR-0003](0003-android-sdk-levels.md)) is met.

No project document or standard states the order of these criteria or the tolerance; they are proposed here. The winner is written in a new record that supersedes this one.

## Consequences

- Good: the choice rests on a measurement on a real phone, not on a preference.
- Bad: model conversion waits for the result.
- The interface of the `inference-runtime` module stays runtime-neutral until the new record exists.
- Model conversion in `ml/conversion/` waits for the new record.

## Affected modules and contracts

`modules/ai/inference-runtime/`, `modules/ai/civic-classifier/`, `modules/ai/semantic-matcher/`, `ml/conversion/`, `ml/manifests/`.

## Enforcement

None by tooling. The choice is made by a device test, which is a process. Its result is recorded in a new decision record that supersedes this one, and its pass criteria then enter the test setup.

## More information

- LiteRT and ONNX Runtime documentation.
- Related: [ADR-0001](0001-application-stack.md), [ADR-0003](0003-android-sdk-levels.md), [ADR-0005](0005-python-environment.md).
