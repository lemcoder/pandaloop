package pl.lemanski.pandaloop

import pl.lemanski.pandaloop.engine.getBytesPerFrame
import pl.lemanski.pandaloop.engine.getChannelCount
import pl.lemanski.pandaloop.engine.getSampleRate
import kotlin.time.Duration.Companion.minutes

enum class TimeSignature {
    COMMON,
    THREE_FOURS
}

fun TimeSignature.getTimeWithTempo(tempo: Int): Int {
    val beat = 1.minutes.inWholeMilliseconds.toInt() / tempo
    return when (this) {
        TimeSignature.COMMON      -> 4 * beat
        TimeSignature.THREE_FOURS -> 3 * beat
    }
}

fun TimeSignature.getBufferSizeInBytesWithTempo(tempo: Int): Long = getSampleRate() * getChannelCount() * getBytesPerFrame() * (getTimeWithTempo(tempo) / 1000L)