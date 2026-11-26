package com.github.pklhub.gradle

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.UnexpectedBuildFailure
import org.junit.jupiter.api.io.TempDir
import kotlin.io.path.createParentDirectories
import kotlin.io.path.readText
import kotlin.io.path.writeText
import java.net.URI
import java.nio.file.Path

abstract class AbstractTest {
//    private val gradleVersion: String? = System.getProperty("testGradleVersion")
    private val gradleVersion: String? = System.getProperty("testGradleVersion")

    private val gradleDistributionUrl: String? = System.getProperty("testGradleDistributionUrl")

    @TempDir protected lateinit var testProjectDir: Path

    protected fun runTask(taskName: String, expectFailure: Boolean = false): BuildResult {
        val runner =
            GradleRunner.create()
                .withProjectDir(testProjectDir.toFile())
                .withArguments("--stacktrace", "--no-build-cache", taskName)
                .withPluginClasspath()
                .withDebug(true)

        if (gradleVersion != null) {
            runner.withGradleVersion(gradleVersion)
        }
        if (gradleDistributionUrl != null) {
            runner.withGradleDistribution(URI(gradleDistributionUrl))
        }

        return try {
            if (expectFailure) runner.buildAndFail() else runner.build()
        } catch (e: UnexpectedBuildFailure) {
            throw AssertionError(e.buildResult.output)
        }
    }

    protected fun writeFile(fileName: String, contents: String): Path {
        return testProjectDir
            .resolve(fileName)
            .createParentDirectories()
            .also { it.writeText(contents.trimIndent()) }
    }

    protected fun checkFileContents(file: Path, contents: String) {
        assertThat(file).exists()
        assertThat(file.readText().trim()).isEqualTo(contents.trim())
    }

    protected fun checkTextContains(text: String, vararg contents: String) {
        for (content in contents) {
            try {
                assertThat(text).contains(content.trimMargin())
            } catch (e: AssertionError) {
                // to get diff output in IDE
                assertThat(text).isEqualTo(content.trimMargin())
            }
        }
    }
}
