package pl.lemanski.pandaloop.utils

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSFileManager

internal actual fun doFileExistAtPath(path: String): Boolean {
    val fileManager = NSFileManager.defaultManager
    return fileManager.fileExistsAtPath(path)
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun deleteFileAtPath(path: String): Boolean {
    val fileManager = NSFileManager.defaultManager

    if (!fileManager.isDeletableFileAtPath(path)) {
        return true
    }

    return fileManager.removeItemAtPath(path, null)
}