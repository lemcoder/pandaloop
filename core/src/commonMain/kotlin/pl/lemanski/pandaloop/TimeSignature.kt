package pl.lemanski.pandaloop

import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

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

