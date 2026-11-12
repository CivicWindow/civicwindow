# discovery

**Owner:** Android/Product lead. **Status:** Planned feature area; a minimal home screen and a build file are drafted, and no build has run yet (the first CI run after publication checks them). No module manifest exists yet.

**Purpose:** Presents locally matched civic records with refresh state and uncertainty.

Before accepting work, define the real inputs/outputs, approved contract versions, dependencies, failure behavior, checks and evidence in a module manifest and architecture/testing documents. The `src/` subfolders are reserved locations, except `src/main`, which holds the first home screen. Build boundaries follow the actual dependency graph rather than the number of folders.
