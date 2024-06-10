package pl.lemanski.pandaloop.core.internal.engine

import pl.lemanski.pandaloop.core.AudioEngine

internal object DefaultAudioEngineOptions : AudioEngine.Options {
    override var channelCount: Int = 1
        private set
    override var sampleRate: Int = 44_100
        private set
    override val bytesPerFrame: Int
        get() = getBytesPerFrame(channelCount)
}