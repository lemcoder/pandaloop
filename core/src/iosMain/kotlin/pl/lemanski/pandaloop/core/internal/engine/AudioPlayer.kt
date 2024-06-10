package pl.lemanski.pandaloop.core.internal.engine

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCValues
import pl.lemanski.pandaloop.engine.get_bytes_per_frame
import pl.lemanski.pandaloop.engine.initialize_playback_device
import pl.lemanski.pandaloop.engine.set_playback_buffer
import pl.lemanski.pandaloop.engine.start_playback
import pl.lemanski.pandaloop.engine.stop_playback
import pl.lemanski.pandaloop.engine.uninitialize_playback_device

@OptIn(ExperimentalForeignApi::class)
internal actual fun initializePlaybackDevice(channelCount: Int, sampleRate: Int) {
    val result = initialize_playback_device(channelCount, sampleRate)
    if (result != 0) {
        throw RuntimeException("Failed to initialize playback device")
    }
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun uninitializePlaybackDevice() {
    uninitialize_playback_device()
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun setPlaybackBuffer(buffer: ByteArray) {
    set_playback_buffer(buffer.toCValues(), buffer.size.toLong())
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun startPlayback() {
    val result = start_playback()
    if (result != 0) {
        throw RuntimeException("Failed to start playback")
    }
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun stopPlayback() {
    stop_playback()
}
