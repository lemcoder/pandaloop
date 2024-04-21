package pl.lemanski.pandaloop.engine

import com.sun.jna.Memory
import com.sun.jna.Native
import com.sun.jna.Pointer

internal actual fun saveAudioFile(path: String, buffer: ByteArray) {
    val pPath: Pointer = Memory(Native.WCHAR_SIZE * (path.length + 1L))
    pPath.setString(0, path)

    val bufferSize = buffer.size
    val pBuffer: Pointer = Memory(bufferSize.toLong())
    for (i in buffer.indices) {
        pBuffer.setByte(i.toLong(), buffer[i])
    }

    val result = NativeInterface.Instance.save_audio_file(pPath, pBuffer, bufferSize.toLong())
    if (result == -1) {
        throw RuntimeException("Failed to save file")
    }
}

internal actual fun loadAudioFile(path: String): ByteArray {
    val pPath: Pointer = Memory(Native.WCHAR_SIZE * (path.length + 1L))
    pPath.setString(0, path)
    val pBufferSize: Pointer = Memory(Native.LONG_SIZE + 1L)

    val resultBuffer = NativeInterface.Instance.load_audio_file(pPath, pBufferSize)

    return resultBuffer.getByteBuffer(0, pBufferSize.getLong(0)).array()
}