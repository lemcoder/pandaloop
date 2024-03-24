package pl.lemanski.pandaloop

import com.sun.jna.Memory

actual object AudioPlayer {
    actual fun initializePlaybackDevice(buffer: ByteArray) {
        // Allocate memory for the buffer
        val sizeInFrames = buffer.size / NativeInterface.Instance.get_bytes_per_frame()
        val memory = Memory(buffer.size.toLong())

        for (i in buffer.indices) {
            memory.setByte(i.toLong(), buffer[i])
        }
        // Call native method passing the Memory pointer
        val result = NativeInterface.Instance.initialize_playback_device(memory, sizeInFrames)
        if (result != 0) {
            throw RuntimeException("Failed to initialize playback device")
        }
    }

    actual fun uninitalizePlaybackDevice() {
        NativeInterface.Instance.uninitalize_playback_device()
    }

    actual fun startPlayback() {
        val result = NativeInterface.Instance.start_playback()
        if (result != 0) {
            throw RuntimeException("Failed to initialize playback device")
        }
    }

    actual fun stopPlayback() {
        NativeInterface.Instance.stop_playback()
    }
}