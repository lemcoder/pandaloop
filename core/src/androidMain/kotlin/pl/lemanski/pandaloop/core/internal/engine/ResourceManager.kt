package pl.lemanski.pandaloop.core.internal.engine

import pl.lemanski.pandaloop.core.engine.jni.PandaLoop

internal actual fun saveAudioFile(path: String, buffer: ByteArray, channelCount: Int, sampleRate: Int) {
    val result = PandaLoop.save_audio_file(path, buffer, buffer.size, channelCount, sampleRate)
    if (result == -1) {
        throw RuntimeException("Failed to save file")
    }
}

internal actual fun loadAudioFile(path: String, fileSize: Long): ByteArray {
    return PandaLoop.load_audio_file(fileSize, path)
}