// Enforcement: `check` runs this test in the build-logic build, and the `android-unit-tests` job of
// CI runs it through `./gradlew :build-logic:test` (open); review checks its format, because
// build-logic is outside ktlintCheck. The job first runs on the first pull request after the
// repository is published; review is the check until then.
// Documentation: JUnit 4; the kotlin-test assertions; Kotlin coding conventions (backticked
// sentence names for tests only).

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Tests for [computeVersionCode], the version-code formula described in
 * docs/operations/RELEASE_PROCESS.md. No test uses a substitute.
 */
class VersionCodeTest {
    @Test
    fun `computes the code from the parts`() {
        assertEquals(1_020_304, computeVersionCode(major = 1, minor = 2, patch = 3, build = 4))
    }

    @Test
    fun `accepts the largest allowed parts`() {
        assertEquals(
            2_099_999_999,
            computeVersionCode(major = 2_099, minor = 99, patch = 99, build = 99)
        )
    }

    @Test
    fun `rejects a part that is out of range`() {
        val major = assertFailsWith<IllegalArgumentException> {
            computeVersionCode(major = 2_100, minor = 0, patch = 0, build = 0)
        }
        assertEquals("MAJOR must be from 0 to 2099, but it is 2100.", major.message)

        val minor = assertFailsWith<IllegalArgumentException> {
            computeVersionCode(major = 0, minor = 100, patch = 0, build = 0)
        }
        assertEquals("MINOR must be from 0 to 99, but it is 100.", minor.message)

        val patch = assertFailsWith<IllegalArgumentException> {
            computeVersionCode(major = 0, minor = 0, patch = 100, build = 0)
        }
        assertEquals("PATCH must be from 0 to 99, but it is 100.", patch.message)

        val build = assertFailsWith<IllegalArgumentException> {
            computeVersionCode(major = 0, minor = 0, patch = 0, build = 100)
        }
        assertEquals("BUILD must be from 0 to 99, but it is 100.", build.message)

        val negative = assertFailsWith<IllegalArgumentException> {
            computeVersionCode(major = -1, minor = 0, patch = 0, build = 0)
        }
        assertEquals("MAJOR must be from 0 to 2099, but it is -1.", negative.message)
    }
}
