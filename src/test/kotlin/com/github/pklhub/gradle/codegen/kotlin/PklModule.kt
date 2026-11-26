package com.github.pklhub.gradle.codegen.kotlin

import kotlin.io.path.createParentDirectories
import kotlin.io.path.writeText
import java.nio.file.Path

data class PklModule(val name: String, val content: String) {
    fun writeToDisk(path: Path): Path {
        return path.createParentDirectories().also { it.writeText(content) }
    }
}
