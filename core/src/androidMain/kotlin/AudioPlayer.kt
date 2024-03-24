package pl.lemanski.pandaloop

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer

actual object AudioPlayer {
    actual fun initializePlaybackMemory(buffer: ByteArray) {
        // Allocate memory for the buffer
        val sizeInFrames = buffer.size / NativeInterface.Instance.get_bytes_per_frame()
        val memory = Memory(buffer.size.toLong())

        for (i in buffer.indices) {
            memory.setByte(i.toLong(), buffer[i])
        }
        // Call native method passing the Memory pointer
        val result = NativeInterface.Instance.initialize_playback_memory(memory, sizeInFrames)
        if (result != 0) {
            throw RuntimeException("Failed to initialize playback memory")
        }
    }

    actual fun initializePlaybackFile(path: String) {
        val pPath: Pointer = Memory(Native.WCHAR_SIZE * (path.length + 1L))
        pPath.setString(0, path)

        val result = NativeInterface.Instance.initialize_playback_file(pPath)
        if (result != 0) {
            throw RuntimeException("Failed to initialize playback file")
        }
    }

    actual fun uninitalizePlayback() {
        NativeInterface.Instance.uninitalize_playback()
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