package pl.lemanski.pandaloop

import pl.lemanski.pandaloop.engine.initializeRecording
import pl.lemanski.pandaloop.engine.startRecording
import pl.lemanski.pandaloop.engine.stopRecording
import pl.lemanski.pandaloop.engine.uninitializeRecording
import pl.lemanski.pandaloop.internal.Closeable
import pl.lemanski.pandaloop.utils.millisToFrames

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