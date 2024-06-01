package pl.lemanski.pandaloop.core.internal.engine

import pl.lemanski.pandaloop.core.engine.jni.PandaLoop

internal actual fun initializePlaybackDevice() {
    val result = PandaLoop.initialize_playback_device(DefaultAudioEngineOptions.channelCount, DefaultAudioEngineOptions.sampleRate)
    if (result != 0) {
        throw RuntimeException("Failed to initialize playback device")
    }
}

internal actual fun startPlayback() {
    val result = PandaLoop.start_playback()
    if (result != 0) {
        throw RuntimeException("Failed to start playback")
    }
}

internal actual fun setPlaybackBuffer(buffer: ByteArray) {
    val result = PandaLoop.set_playback_buffer(buffer, buffer.size.toLong())
    if (result != 0) {
        throw RuntimeException("Failed to mix sound from memory")
    }
}

internal actual fun uninitializePlaybackDevice() {
    PandaLoop.uninitialize_playback_device()
}

internal actual fun stopPlayback() {
    PandaLoop.stop_playback()
}
