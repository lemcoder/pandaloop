package pl.lemanski.pandaloop.engine

internal expect fun saveAudioFile(path: String, buffer: ByteArray)

internal expect fun loadAudioFile(path: String): ByteArray