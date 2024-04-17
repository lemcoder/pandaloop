package pl.lemanski.pandaloop

import pl.lemanski.pandaloop.engine.initializePlaybackDevice
import pl.lemanski.pandaloop.engine.mixPlaybackFile
import pl.lemanski.pandaloop.engine.mixPlaybackMemory
import pl.lemanski.pandaloop.engine.startPlayback
import pl.lemanski.pandaloop.engine.stopPlayback
import pl.lemanski.pandaloop.engine.uninitializePlaybackDevice
import pl.lemanski.pandaloop.internal.Closeable

class Loop : Closeable {
    private var track: Int = 0
    private var state: State = State.IDLE

    init {
        initializePlaybackDevice()
    }

    fun mixBuffer(buffer: ByteArray) {
        if (state == State.IDLE) {
            mixPlaybackMemory(buffer, track)
        } else {
            throw InvalidOperationException()
        }
    }

    fun mixFile(path: String) {
        if (state == State.IDLE) {
            mixPlaybackFile(path, track)
        } else {
            throw InvalidOperationException()
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

    fun setTrack(track: Int) {
        this.track = track.coerceIn(0..3) // FIXME add setter for max track number
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