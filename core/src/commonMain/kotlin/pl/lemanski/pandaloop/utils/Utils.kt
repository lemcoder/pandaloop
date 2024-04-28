package pl.lemanski.pandaloop.utils

// FIXME fixed sample rate of 44100
internal fun millisToFrames(millis: Int): Int = 44_100 * (millis / 1_000) // SAMPLE_RATE / 1000