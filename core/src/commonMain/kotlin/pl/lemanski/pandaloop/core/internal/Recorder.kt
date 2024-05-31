package pl.lemanski.pandaloop.core.internal

import pl.lemanski.pandaloop.core.Recording
import pl.lemanski.pandaloop.core.engine.initializeRecording
import pl.lemanski.pandaloop.core.engine.startRecording
import pl.lemanski.pandaloop.core.engine.stopRecording
import pl.lemanski.pandaloop.core.engine.uninitializeRecording

internal class Recorder(private val recordingBufferSize: Long) : Recording, Closeable {
    init {
        initializeRecording(recordingBufferSize)
    }

    override fun start() {
        startRecording()
    }

    override fun stop(): ByteArray {
        return stopRecording(recordingBufferSize)
    }

    override fun close() {
        uninitializeRecording()
    }
}