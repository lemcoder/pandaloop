package pl.lemanski.pandaloop.core.internal.engine

internal expect fun saveAudioFile(path: String, buffer: ByteArray, channelCount: Int, sampleRate: Int)

internal expect fun loadAudioFile(path: String, fileSize: Long): ByteArray