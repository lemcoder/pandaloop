package pl.lemanski.pandaloop.core.engine

import pl.lemanski.pandaloop.core.PandaLoopContext
import pl.lemanski.pandaloop.core.engine.jni.PandaLoop
import pl.lemanski.pandaloop.dsp.utils.toFloatArray

internal actual fun initializePlaybackDevice() {
    val result = PandaLoop.initialize_playback_device(PandaLoopContext.channelCount, PandaLoopContext.sampleRate)
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
    val result = PandaLoop.set_playback_buffer(buffer.toFloatArray(), buffer.size.toLong())
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
