package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.engine.initializePlaybackDevice
import pl.lemanski.pandaloop.core.engine.setPlaybackBuffer
import pl.lemanski.pandaloop.core.engine.startPlayback
import pl.lemanski.pandaloop.core.engine.stopPlayback
import pl.lemanski.pandaloop.core.engine.uninitializePlaybackDevice
import pl.lemanski.pandaloop.core.internal.Closeable

class Loop : Closeable {
    private var state: State = State.IDLE

    init {
        initializePlaybackDevice()
    }

    fun setBuffer(buffer: ByteArray) {
        if (state == State.IDLE) {
            setPlaybackBuffer(buffer)
        } else {
            throw InvalidOperationException("Cannot set buffer while playing")
        }
    }

    fun play() {
        startPlayback()
        state = State.PLAYBACK
    }

    fun stop() {
        stopPlayback()
        state = State.IDLE
    }

    override fun close() {
        uninitializePlaybackDevice()
        state = State.IDLE
    }

    private enum class State {
        PLAYBACK,
        IDLE
    }
}