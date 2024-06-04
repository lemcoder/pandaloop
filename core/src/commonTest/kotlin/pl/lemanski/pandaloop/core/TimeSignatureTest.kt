package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.internal.engine.DefaultAudioEngine
import pl.lemanski.pandaloop.core.internal.engine.DefaultAudioEngineOptions
import pl.lemanski.pandaloop.core.utils.getBufferSizeInBytesWithTempo
import pl.lemanski.pandaloop.core.utils.getTimeWithTempo
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeSignatureTest {
    @Test
    fun shouldCalculateCorrectAmountOfMilliseconds() {
        assertEquals(2666.6666666666665, TimeSignature.COMMON.getTimeWithTempo(90))
        assertEquals(4_000.0, TimeSignature.COMMON.getTimeWithTempo(60))
        assertEquals(3_000.0, TimeSignature.THREE_FOURS.getTimeWithTempo(60))
    }

    @Test
    fun shouldGetCorrectBufferSize() {
        DefaultAudioEngineOptions.sampleRate
        val testOptions = object : AudioEngine.Options {
            override val channelCount: Int = 1
            override val sampleRate: Int = 44_100
            override val bytesPerFrame: Int = 4

        }
        val pandaLoop = PandaLoop(engine = DefaultAudioEngine(testOptions))
        var bufferSize = pandaLoop.getBufferSizeInBytesWithTempo(TimeSignature.COMMON, 90)
        assertEquals(470_804.0, bufferSize.toDouble(), 500.0) // FIXME
        bufferSize = pandaLoop.getBufferSizeInBytesWithTempo(TimeSignature.COMMON, 60)
        assertEquals(705_600.0, bufferSize.toDouble(), 500.0) // FIXME
    }
}