# CivicWindow project overview

**Status:** Selected capstone project with its three leads assigned. Source coverage and device performance are not confirmed.

CivicWindow is a native Android application for residents to discover municipal and state government activity relevant to their chosen jurisdictions and interests. Connectors retrieve public records, normalize them and store them locally. On-device models classify and match downloaded records. Results link to official source material and distinguish source text from model inferences.

The core needs no mandatory account, recurring subscription or hosted AI inference service. New records and initial model acquisition need network access. Offline access applies to already downloaded records. Background refresh and notifications are best effort; the app should show last successful refresh and allow manual refresh. Exports are user directed Evidence Packs.

The three development functions are AI/ML, Android/Product and Data Integration/Platform, each with an assigned lead. Category A is governed by the Software Architecture Month 2 course deadline; later work and release coverage remain subject to feasibility evidence. See the master pitch v1.3 in the separate COS359 planning workspace for fuller product context.

The [application build plan](../architecture/APPLICATION_BUILD_PLAN.md) maps the pitch to the proposed application composition and evidence required for each core feature.
