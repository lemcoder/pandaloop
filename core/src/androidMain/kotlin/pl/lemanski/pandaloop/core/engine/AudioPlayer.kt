package pl.lemanski.pandaloop.core.engine

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

internal actual fun setPlaybackBuffer(buffer: ByteArray) {
    val sizeInFrames = buffer.size / NativeInterface.Instance.get_bytes_per_frame()
    val memory = Memory(buffer.size.toLong())

    for (i in buffer.indices) {
        memory.setByte(i.toLong(), buffer[i])
    }
    val result = NativeInterface.Instance.set_playback_buffer(memory, sizeInFrames)
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
