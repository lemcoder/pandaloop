package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.engine.loadAudioFile
import pl.lemanski.pandaloop.core.engine.saveAudioFile
import pl.lemanski.pandaloop.core.utils.deleteFileAtPath
import pl.lemanski.pandaloop.core.utils.doFileExistAtPath

class AudioFile(
    private val path: String
) {
    fun exists(): Boolean {
        return doFileExistAtPath(path)
    }

    fun saveBuffer(buffer: ByteArray) {
        saveAudioFile(this.path, buffer)
    }

    fun fileLength(): Long {
        TODO()
    }

    fun load(): ByteArray {
        return loadAudioFile(path, fileLength())
    }

    fun delete(): Boolean {
        return deleteFileAtPath(path)
    }
}