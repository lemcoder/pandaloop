package pl.lemanski.pandaloop

import pl.lemanski.pandaloop.engine.getBytesPerFrame
import pl.lemanski.pandaloop.engine.getChannelCount
import pl.lemanski.pandaloop.engine.getSampleRate
import kotlin.time.Duration.Companion.minutes

sealed interface TimeSignature {
    fun getTimeWithTempo(tempo: Int): Int
}

data object Common : TimeSignature {
    override fun getTimeWithTempo(tempo: Int): Int {
        val beat = 1.minutes.inWholeMilliseconds.toInt() / tempo
        return 4 * beat
    }
}

data object ThreeFours : TimeSignature {
    override fun getTimeWithTempo(tempo: Int): Int {
        val beat = 1.minutes.inWholeMilliseconds.toInt() / tempo
        return 3 * beat
    }
}

fun TimeSignature.getBufferSizeInBytesWithTempo(tempo: Int): Long = getSampleRate() * getChannelCount() * getBytesPerFrame() * (getTimeWithTempo(tempo) / 1000L)