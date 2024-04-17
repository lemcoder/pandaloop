package pl.lemanski.pandaloop

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.lemanski.pandaloop.engine.initializeRecording
import pl.lemanski.pandaloop.engine.startRecording
import pl.lemanski.pandaloop.engine.stopRecording
import pl.lemanski.pandaloop.engine.uninitializeRecording
import pl.lemanski.pandaloop.internal.Closeable
import pl.lemanski.pandaloop.utils.millisToFrames

class AudioRecording internal constructor(recordingTimeMs: Int) : Closeable {
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

fun recordLoop(timeSignature: TimeSignature, tempo: Int, bars: Int = 1, onRecordingEnd: (ByteArray) -> Unit): Job {
    val recordingJob = Job()
    CoroutineScope(recordingJob).launch {
        withContext(Dispatchers.IO) {
            val totalTimeMs = timeSignature.getTimeWithTempo(tempo) * bars
            val audioRecording = AudioRecording(totalTimeMs)

            with(audioRecording) {
                start()
                delay(totalTimeMs.toLong())
                stop()
                close()
            }

            onRecordingEnd(audioRecording.recordedBuffer)
        }
    }

    return recordingJob
}