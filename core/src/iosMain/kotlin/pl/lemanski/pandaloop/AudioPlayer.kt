package pl.lemanski.pandaloop

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import kotlinx.cinterop.toCValues
import pl.lemanski.pandaloop.engine.start_playback
import pl.lemanski.pandaloop.engine.stop_playback
import pl.lemanski.pandaloop.engine.initialize_playback_device
import pl.lemanski.pandaloop.engine.mix_playback_file
import pl.lemanski.pandaloop.engine.mix_playback_memory
import pl.lemanski.pandaloop.engine.uninitialize_playback_device
import pl.lemanski.pandaloop.engine.get_bytes_per_frame

@OptIn(ExperimentalForeignApi::class)
actual object AudioPlayer {
    actual fun initializePlaybackDevice() {
        val result = initialize_playback_device()
        if (result != 0) {
            throw RuntimeException("Failed to initialize playback device")
        }
    }

    actual fun uninitializePlaybackDevice() {
        uninitialize_playback_device()
    }

    actual fun mixPlaybackMemory(buffer: ByteArray, trackNumber: Int) {
        val sizeInFrames = buffer.size / get_bytes_per_frame()
        mix_playback_memory(buffer.toCValues(), sizeInFrames, trackNumber)
    }

    actual fun mixPlaybackFile(path: String, trackNumber: Int) {
        mix_playback_file(path.cstr, trackNumber)
    }

    actual fun startPlayback() {
        val result = start_playback()
        if (result != 0) {
            throw RuntimeException("Failed to start playback")
        }
    }

    actual fun stopPlayback() {
        stop_playback()
    }
}