package pl.lemanski.pandaloop.utils

import pl.lemanski.pandaloop.engine.getChannelCount
import pl.lemanski.pandaloop.engine.getSampleRate

internal fun millisToFrames(millis: Int): Int = getSampleRate() * (millis.coerceIn(0, Int.MAX_VALUE) / 1_000) * getChannelCount()