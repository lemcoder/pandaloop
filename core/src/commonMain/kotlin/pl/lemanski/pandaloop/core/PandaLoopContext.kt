package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.engine.stopPlayback
import pl.lemanski.pandaloop.core.engine.stopRecording
import pl.lemanski.pandaloop.core.engine.uninitializePlaybackDevice
import pl.lemanski.pandaloop.core.engine.uninitializeRecording

object PandaLoopContext {
    var channelCount: Int = 1
        private set
    var sampleRate: Int = 44_100
        private set

    fun configure(channelCount: Int, sampleRate: Int) {
        // Ensure everything is stopped before configuring
        kotlin.runCatching {
            stopPlayback()
            uninitializePlaybackDevice()
            stopRecording(0)
            uninitializeRecording()
        }

        this.channelCount = channelCount
        this.sampleRate = sampleRate
    }
}