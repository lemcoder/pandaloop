package pl.lemanski.pandaloop.core.engine

import pl.lemanski.pandaloop.core.PandaLoopContext
import pl.lemanski.pandaloop.core.engine.jni.PandaLoop
import pl.lemanski.pandaloop.dsp.utils.toByteArray
import pl.lemanski.pandaloop.dsp.utils.toFloatArray

internal actual fun saveAudioFile(path: String, buffer: ByteArray) {
    val result = PandaLoop.save_audio_file(path, buffer.toFloatArray(), buffer.size, PandaLoopContext.channelCount, PandaLoopContext.sampleRate)
    if (result == -1) {
        throw RuntimeException("Failed to save file")
    }
}

internal actual fun loadAudioFile(path: String, fileSize: Long): ByteArray {
    return PandaLoop.load_audio_file(fileSize, path).toByteArray()
}