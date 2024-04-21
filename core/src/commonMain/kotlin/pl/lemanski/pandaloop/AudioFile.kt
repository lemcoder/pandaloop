package pl.lemanski.pandaloop

import pl.lemanski.pandaloop.engine.loadAudioFile
import pl.lemanski.pandaloop.engine.saveAudioFile
import pl.lemanski.pandaloop.utils.deleteFileAtPath
import pl.lemanski.pandaloop.utils.doFileExistAtPath

class AudioFile(
    private val path: String
) {
    fun exists(): Boolean {
        return doFileExistAtPath(path)
    }

    fun saveBuffer(buffer: ByteArray) {
        saveAudioFile(this.path, buffer)
    }

    fun load(): ByteArray {
        return loadAudioFile(path)
    }

    fun delete(): Boolean {
        return deleteFileAtPath(path)
    }
}