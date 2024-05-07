package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.engine.initializeRecording
import pl.lemanski.pandaloop.core.engine.startRecording
import pl.lemanski.pandaloop.core.engine.stopRecording
import pl.lemanski.pandaloop.core.engine.uninitializeRecording
import pl.lemanski.pandaloop.core.internal.Closeable
import pl.lemanski.pandaloop.core.utils.millisToFrames

class Recording(recordingTimeMs: Int) : Closeable {
    private val frameCount: Int = millisToFrames(recordingTimeMs)
    var recordedBuffer: ByteArray = byteArrayOf()
        private set

    init {
        initializeRecording(frameCount)
    }

    fun start() {
        startRecording()
    }

    fun stop() {
        recordedBuffer = stopRecording(frameCount)
    }

    override fun close() {
        uninitializeRecording()
    }
}