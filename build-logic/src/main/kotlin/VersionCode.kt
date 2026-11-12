// Enforcement: the application plugin calls this function for versionCode and the build fails with
// a message on an out-of-range part; VersionCodeTest checks the formula and the ranges, and runs
// with the build-logic tests.

private const val PART_LIMIT = 100
private const val MAJOR_LIMIT = 2_100

/**
 * Computes the Android version code from the version parts and the build count:
 * MAJOR x 1,000,000 + MINOR x 10,000 + PATCH x 100 + BUILD.
 *
 * MINOR, PATCH and BUILD each run from 0 to 99 and MAJOR from 0 to 2,099, so no two versions share
 * a code and the largest code, 2,099,999,999, stays below 2,100,000,000, the limit Android allows.
 * The limit is stated on the "Version your app" page of Android Developers:
 * https://developer.android.com/studio/publish/versioning
 * The formula is described in docs/operations/RELEASE_PROCESS.md; no standard covers it, it is the
 * project's choice. This is a plain function with no Gradle types so that it can be tested alone.
 *
 * @throws IllegalArgumentException with a message naming the part that is out of range.
 */
fun computeVersionCode(major: Int, minor: Int, patch: Int, build: Int): Int {
    require(
        major in 0 until MAJOR_LIMIT
    ) { "MAJOR must be from 0 to ${MAJOR_LIMIT - 1}, but it is $major." }
    require(
        minor in 0 until PART_LIMIT
    ) { "MINOR must be from 0 to ${PART_LIMIT - 1}, but it is $minor." }
    require(
        patch in 0 until PART_LIMIT
    ) { "PATCH must be from 0 to ${PART_LIMIT - 1}, but it is $patch." }
    require(
        build in 0 until PART_LIMIT
    ) { "BUILD must be from 0 to ${PART_LIMIT - 1}, but it is $build." }
    return major * 1_000_000 + minor * 10_000 + patch * 100 + build
}
