package pl.lemanski.pandaloop.core.internal.engine

internal expect fun initializePlaybackDevice(channelCount: Int, sampleRate: Int)
internal expect fun uninitializePlaybackDevice()
internal expect fun setPlaybackBuffer(buffer: ByteArray)
internal expect fun startPlayback()
internal expect fun stopPlayback()
