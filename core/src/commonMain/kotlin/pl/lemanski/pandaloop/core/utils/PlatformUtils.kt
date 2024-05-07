package pl.lemanski.pandaloop.core.utils

internal expect fun doFileExistAtPath(path: String): Boolean

internal expect fun deleteFileAtPath(path: String): Boolean