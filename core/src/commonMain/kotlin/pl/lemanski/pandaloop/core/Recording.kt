package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.internal.Recorder

interface Recording {
    fun start()
    fun stop(): ByteArray
}

fun recording(bufferSize: Long): Recording {
    return Recorder(bufferSize)
}