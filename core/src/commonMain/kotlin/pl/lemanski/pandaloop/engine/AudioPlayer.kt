package pl.lemanski.pandaloop.engine

internal expect fun initializePlaybackDevice()
internal expect fun uninitializePlaybackDevice()
internal expect fun mixPlaybackMemory(buffer: ByteArray, trackNumber: Int)
internal expect fun mixPlaybackFile(path: String, trackNumber: Int)
internal expect fun startPlayback()
internal expect fun stopPlayback()
