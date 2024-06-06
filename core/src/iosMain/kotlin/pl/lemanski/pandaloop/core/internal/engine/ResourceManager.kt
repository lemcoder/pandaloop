package pl.lemanski.pandaloop.core.internal.engine

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCValues
import pl.lemanski.pandaloop.engine.save_audio_file

@OptIn(ExperimentalForeignApi::class)
internal actual fun saveAudioFile(path: String, buffer: ByteArray, channelCount: Int, sampleRate: Int) {
    val result = save_audio_file(path, buffer.toCValues(), buffer.size, channelCount, sampleRate)
    if (result == -1) {
        throw RuntimeException("Failed to save file")
    }
}

// TODO
internal actual fun loadAudioFile(path: String, fileSize: Long): ByteArray {
    return byteArrayOf()
}