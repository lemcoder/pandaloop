package pl.lemanski.pandaloop.core.utils

import pl.lemanski.pandaloop.core.PandaLoopContext

internal fun millisToFrames(millis: Int): Int = PandaLoopContext.sampleRate * PandaLoopContext.channelCount * (millis.coerceIn(0, Int.MAX_VALUE) / 1_000)