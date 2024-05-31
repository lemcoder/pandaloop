package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.internal.Looper

interface Loop {
    fun updatePlaybackBuffer(buffer: ByteArray)
    fun play()
    fun pause()
}

fun loop(playbackBuffer: ByteArray): Loop {
    val looper = Looper()
    looper.updatePlaybackBuffer(playbackBuffer)
    return looper
}