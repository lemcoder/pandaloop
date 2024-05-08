package pl.lemanski.pandaloop.core.engine

internal expect fun initializePlaybackDevice()
internal expect fun uninitializePlaybackDevice()
internal expect fun setPlaybackBuffer(buffer: ByteArray)
internal expect fun startPlayback()
internal expect fun stopPlayback()