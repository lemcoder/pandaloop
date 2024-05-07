package pl.lemanski.pandaloop.core.utils

import java.io.File

internal actual fun doFileExistAtPath(path: String): Boolean {
    return File(path).exists()
}

internal actual fun deleteFileAtPath(path: String): Boolean {
    val file = File(path)

    // Assume success if file did not exist before
    if (!file.exists()) return true

    return file.delete()
}