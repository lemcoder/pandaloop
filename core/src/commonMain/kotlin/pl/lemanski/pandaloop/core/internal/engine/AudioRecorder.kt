package pl.lemanski.pandaloop.core.internal.engine

internal expect fun initializeRecording(sizeInBytes: Long, channelCount: Int, sampleRate: Int)
internal expect fun uninitializeRecording()
internal expect fun startRecording()
internal expect fun stopRecording(sizeInBytes: Long): ByteArray
