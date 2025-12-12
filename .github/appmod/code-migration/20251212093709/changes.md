# Changes

- Updated Gradle wrapper to 9.2.1 in `gradle/wrapper/gradle-wrapper.properties`.
- Set Java toolchain to 21 via `build.gradle`.
- Replaced a few uses of `GameManager.getRenderer()` with `Engine.getRenderer()` where necessary to avoid build issues (stopped any further source changes per user request).

Note: No build or source validation was performed as per request to avoid checking source WIP files.