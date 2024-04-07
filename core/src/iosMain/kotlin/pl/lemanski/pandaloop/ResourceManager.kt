package pl.lemanski.pandaloop

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toCValues
import pl.lemanski.pandaloop.engine.save_audio_file

actual object ResourceManager {
    @OptIn(ExperimentalForeignApi::class)
    actual fun saveAudioFile(path: String, buffer: ByteArray) {
        val result = save_audio_file(path, buffer.toCValues(), buffer.size)
        if (result == -1) {
            throw RuntimeException("Failed to save file")
        }
    }
}