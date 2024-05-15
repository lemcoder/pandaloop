package pl.lemanski.pandaloop.core.engine

internal expect fun initializeRecording(sizeInBytes: Long)
internal expect fun uninitializeRecording()
internal expect fun startRecording()
internal expect fun stopRecording(sizeInBytes: Long): ByteArray
