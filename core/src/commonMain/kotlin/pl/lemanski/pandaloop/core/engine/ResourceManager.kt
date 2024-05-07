package pl.lemanski.pandaloop.core.engine

internal expect fun saveAudioFile(path: String, buffer: ByteArray)

internal expect fun loadAudioFile(path: String): ByteArray