package pl.lemanski.pandaloop

import com.sun.jna.Memory
import com.sun.jna.Native

actual object AudioPlayer {
    actual fun initializePlaybackDevice(buffer: ByteArray) {
        // Allocate memory for the buffer
        val memory = Memory(buffer.size.toLong())

        for (i in buffer.indices) {
            memory.setByte(i.toLong(), buffer[i])
        }
        // Call native method passing the Memory pointer
        val result = NativeInterface.Instance.initializePlaybackDevice(memory, buffer.size)
        if (result != 0) {
            throw RuntimeException("Failed to initialize playback device")
        }
    }

    actual fun uninitalizePlaybackDevice() {
        NativeInterface.Instance.uninitalizePlaybackDevice()
    }

    actual fun startPlayback() {
        val result = NativeInterface.Instance.startPlayback()
        if (result != 0) {
            throw RuntimeException("Failed to initialize playback device")
        }
    }

    actual fun stopPlayback() {
        NativeInterface.Instance.stopPlayback()
    }
}