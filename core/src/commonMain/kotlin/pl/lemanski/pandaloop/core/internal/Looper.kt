package pl.lemanski.pandaloop.core.internal

import pl.lemanski.pandaloop.core.Loop
import pl.lemanski.pandaloop.core.engine.initializePlaybackDevice
import pl.lemanski.pandaloop.core.engine.setPlaybackBuffer
import pl.lemanski.pandaloop.core.engine.startPlayback
import pl.lemanski.pandaloop.core.engine.stopPlayback
import pl.lemanski.pandaloop.core.engine.uninitializePlaybackDevice

internal class Looper : Loop, Closeable {
    private var state: State = State.IDLE

    init {
        initializePlaybackDevice()
    }

    override fun updatePlaybackBuffer(buffer: ByteArray) {
        if (state == State.IDLE) {
            setPlaybackBuffer(buffer)
        } else {
            throw IllegalStateException("Cannot set buffer while playing")
        }
    }

    override fun play() {
        startPlayback()
        state = State.PLAYBACK
    }

    override fun pause() {
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