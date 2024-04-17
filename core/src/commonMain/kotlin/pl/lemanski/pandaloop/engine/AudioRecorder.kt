package pl.lemanski.pandaloop.engine

internal expect fun initializeRecording(sizeInFrames: Int)
internal expect fun uninitializeRecording()
internal expect fun startRecording()
internal expect fun stopRecording(sizeInFrames: Int): ByteArray
