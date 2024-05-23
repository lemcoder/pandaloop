package pl.lemanski.pandaloop.core.engine

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCValues
import pl.lemanski.pandaloop.engine.save_audio_file

@OptIn(ExperimentalForeignApi::class)
internal actual fun saveAudioFile(path: String, buffer: ByteArray) {
    val result = save_audio_file(path, buffer.toCValues(), buffer.size)
    if (result == -1) {
        throw RuntimeException("Failed to save file")
    }
}

// TODO
internal actual fun loadAudioFile(path: String, fileSize: Long): ByteArray {
    return byteArrayOf()
}