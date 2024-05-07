package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.engine.getBytesPerFrame
import pl.lemanski.pandaloop.core.engine.getChannelCount
import pl.lemanski.pandaloop.core.engine.getSampleRate
import kotlin.time.Duration.Companion.minutes

enum class TimeSignature {
    COMMON,
    THREE_FOURS
}

fun TimeSignature.getTimeWithTempo(tempo: Int): Long {
    val beat = 1.minutes.inWholeMilliseconds.toInt() / tempo
    return when (this) {
        TimeSignature.COMMON      -> 4L * beat
        TimeSignature.THREE_FOURS -> 3L * beat
    }
}

fun TimeSignature.getBufferSizeInBytesWithTempo(tempo: Int): Long = getSampleRate() * getChannelCount() * getBytesPerFrame() * (getTimeWithTempo(tempo) / 1000L)