package pl.lemanski.pandaloop.core.engine

internal expect fun initializeRecording(sizeInFrames: Int)
internal expect fun uninitializeRecording()
internal expect fun startRecording()
internal expect fun stopRecording(sizeInFrames: Int): ByteArray
