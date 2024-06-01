package pl.lemanski.pandaloop.core.internal.engine

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCValues
import pl.lemanski.pandaloop.engine.get_bytes_per_frame
import pl.lemanski.pandaloop.engine.initialize_playback_device
import pl.lemanski.pandaloop.engine.start_playback
import pl.lemanski.pandaloop.engine.stop_playback
import pl.lemanski.pandaloop.engine.uninitialize_playback_device

@OptIn(ExperimentalForeignApi::class)
internal actual fun initializePlaybackDevice() {
    val result = initialize_playback_device()
    if (result != 0) {
        throw RuntimeException("Failed to initialize playback device")
    }
}

internal actual fun uninitializePlaybackDevice() {
    uninitialize_playback_device()
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun setPlaybackBuffer(buffer: ByteArray) {
    val sizeInFrames = buffer.size / get_bytes_per_frame()
    set_playback_buffer(buffer.toCValues(), sizeInFrames)
}

internal actual fun startPlayback() {
    val result = start_playback()
    if (result != 0) {
        throw RuntimeException("Failed to start playback")
    }
}

internal actual fun stopPlayback() {
    stop_playback()
}
