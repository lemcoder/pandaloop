package pl.lemanski.pandaloop.core.internal.engine

internal expect fun saveAudioFile(path: String, buffer: ByteArray)

internal expect fun loadAudioFile(path: String, fileSize: Long): ByteArray