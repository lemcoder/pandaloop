package pl.lemanski.pandaloop

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer

actual object ResourceManager {

    actual fun saveAudioFile(path: String, buffer: FloatArray) {
        val floatSize = Native.getNativeSize(Float::class.java)
        val pPath: Pointer = Memory(Native.WCHAR_SIZE * (path.length + 1L))
        pPath.setString(0, path)

        val pBuffer: Pointer = Memory(floatSize * buffer.size.toLong())
        for (i in buffer.indices) {
            pBuffer.setFloat((i * floatSize).toLong(), buffer[i])
        }

        val result = NativeInterface.Instance.saveAudioFile(pPath, pBuffer)
        if (result == -1) {
            throw RuntimeException("Failed to save file")
        }
    }

    actual fun loadAudioFile(path: String): FloatArray {
        val pPath: Pointer = Memory(Native.WCHAR_SIZE * (path.length + 1L))
        pPath.setString(0, path)
        val pointer = NativeInterface.Instance.loadAudioFile(pPath)

        // TODO move the logic into separate method
        val bufferSize = 1024 // Initial buffer size
        val buffer = FloatArray(bufferSize)
        var totalFramesRead = 0
        var framesRead: Int

        while (true) {
            val readChunk = pointer.getFloatArray(totalFramesRead * 2L, bufferSize)
            framesRead = readChunk.size / 2

            if (framesRead == 0) {
                break
            }

            if (totalFramesRead + framesRead > buffer.size) {
                buffer.copyOf(buffer.size * 2)
            }

            readChunk.copyInto(buffer, totalFramesRead * 2)
            totalFramesRead += framesRead
        }

        return buffer.copyOf(totalFramesRead * 2)
    }
}