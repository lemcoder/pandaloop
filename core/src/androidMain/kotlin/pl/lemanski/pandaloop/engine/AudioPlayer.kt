package pl.lemanski.pandaloop.engine

import com.sun.jna.Memory

internal actual fun initializePlaybackDevice() {
    val result = NativeInterface.Instance.initialize_playback_device()
    if (result != 0) {
        throw RuntimeException("Failed to initialize playback device")
    }
}

internal actual fun startPlayback() {
    val result = NativeInterface.Instance.start_playback()
    if (result != 0) {
        throw RuntimeException("Failed to start playback")
    }
}

internal actual fun mixPlaybackMemory(buffer: ByteArray, trackNumber: Int) {
    val sizeInFrames = buffer.size / NativeInterface.Instance.get_bytes_per_frame()
    val memory = Memory(buffer.size.toLong())

    for (i in buffer.indices) {
        memory.setByte(i.toLong(), buffer[i])
    }
    val result = NativeInterface.Instance.mix_playback_memory(memory, sizeInFrames, trackNumber)
    if (result != 0) {
        throw RuntimeException("Failed to mix sound from memory")
    }
}

internal actual fun uninitializePlaybackDevice() {
    NativeInterface.Instance.uninitialize_playback_device()
}

internal actual fun stopPlayback() {
    NativeInterface.Instance.stop_playback()
}
