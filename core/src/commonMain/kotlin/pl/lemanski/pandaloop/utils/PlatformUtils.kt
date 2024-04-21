package pl.lemanski.pandaloop.utils

internal expect fun doFileExistAtPath(path: String): Boolean

internal expect fun deleteFileAtPath(path: String): Boolean