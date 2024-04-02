package pl.lemanski.pandaloop

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer

actual object AudioPlayer {
    actual fun initializePlaybackDevice() {
        val result = NativeInterface.Instance.initialize_playback_device()
        if (result != 0) {
            throw RuntimeException("Failed to initialize playback device")
        }
    }

    actual fun startPlayback() {
        val result = NativeInterface.Instance.start_playback()
        if (result != 0) {
            throw RuntimeException("Failed to start playback")
        }
    }


    actual fun mixPlaybackMemory(buffer: ByteArray, trackNumber: Int) {
        // Allocate memory for the buffer
        val sizeInFrames = buffer.size / NativeInterface.Instance.get_bytes_per_frame()
        val memory = Memory(buffer.size.toLong())

        for (i in buffer.indices) {
            memory.setByte(i.toLong(), buffer[i])
        }
        // Call native method passing the Memory pointer
        val result = NativeInterface.Instance.mix_playback_memory(memory, sizeInFrames, trackNumber)
        if (result != 0) {
            throw RuntimeException("Failed to mix sound from memory")
        }
    }

    actual fun mixPlaybackFile(path: String, trackNumber: Int) {
        val pPath: Pointer = Memory(Native.WCHAR_SIZE * (path.length + 1L))
        pPath.setString(0, path)

        val result = NativeInterface.Instance.mix_playback_file(pPath, trackNumber)
        if (result != 0) {
            throw RuntimeException("Failed to mix sound from file")
        }
    }

    actual fun uninitalizePlaybackDevice() {
        NativeInterface.Instance.uninitalize_playback_device()
    }


    actual fun stopPlayback() {
        NativeInterface.Instance.stop_playback()
    }
}