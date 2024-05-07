package pl.lemanski.pandaloop.core.utils

import pl.lemanski.pandaloop.core.engine.getChannelCount
import pl.lemanski.pandaloop.core.engine.getSampleRate

internal fun millisToFrames(millis: Int): Int = getSampleRate() * (millis.coerceIn(0, Int.MAX_VALUE) / 1_000) * getChannelCount()