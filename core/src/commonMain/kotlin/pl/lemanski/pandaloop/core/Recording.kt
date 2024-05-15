package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.engine.initializeRecording
import pl.lemanski.pandaloop.core.engine.startRecording
import pl.lemanski.pandaloop.core.engine.stopRecording
import pl.lemanski.pandaloop.core.engine.uninitializeRecording
import pl.lemanski.pandaloop.core.internal.Closeable

class Recording(private val recordingBufferSize: Long) : Closeable {
    var recordedBuffer: ByteArray = byteArrayOf()
        private set

    init {
        initializeRecording(recordingBufferSize)
    }

    fun start() {
        startRecording()
    }

    fun stop() {
        recordedBuffer = stopRecording(recordingBufferSize)
    }

    override fun close() {
        uninitializeRecording()
    }
}