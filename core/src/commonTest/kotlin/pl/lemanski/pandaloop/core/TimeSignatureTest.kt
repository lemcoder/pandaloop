package pl.lemanski.pandaloop.core

import kotlin.test.Test
import kotlin.test.assertEquals

class TimeSignatureTest {
    @Test
    fun shouldCalculateCorrectAmountOfMilliseconds() {
        assertEquals(2666.6666666666665, TimeSignature.COMMON.getTimeWithTempo(90))
        assertEquals(3_000.0, TimeSignature.THREE_FOURS.getTimeWithTempo(60))
    }

    @Test
    fun shouldGetCorrectBufferSize() {
        PandaLoopContext.sampleRate
        val bufferSize = TimeSignature.COMMON.getBufferSizeInBytesWithTempo(90, 4)
        assertEquals(470_804.0, bufferSize.toDouble(), 500.0)
    }
}