package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.internal.engine.DefaultAudioEngine
import pl.lemanski.pandaloop.core.utils.getTimeWithTempo
import kotlin.math.round

class PandaLoop(
    internal val engine: AudioEngine = DefaultAudioEngine(),
) {
    /**
     * Set the buffer to be played. Should be called before starting playback.
     *
     * @param buffer buffer to be played
     */
    fun setPlaybackBuffer(buffer: ByteArray) {
        engine.initializePlaybackDevice()
        engine.setPlaybackBuffer(buffer)
    }

    /**
     * Start playback of the set buffer.
     */
    fun startPlayback() {
        engine.startPlayback()
    }

    /**
     * Pause playback of the set buffer.
     */
    fun pausePlayback() {
        engine.stopPlayback()
    }

    /**
     * Stop playback of the set buffer. Should be called after stopping playback.
     * This will also uninitialize the playback device, so after this call, setPlaybackBuffer
     * should be called again to set a new buffer.
     */
    fun stopPlayback() {
        engine.setPlaybackBuffer(byteArrayOf())
        engine.uninitializePlaybackDevice()
    }

    /**
     * Set the buffer to be recorded and starts recording.
     * Buffer size should be kept during the recording to be able to retrieve it later.
     *
     * @param sizeInBytes buffer size in bytes
     */
    fun startRecording(sizeInBytes: Long) {
        engine.initializeRecording(sizeInBytes)
        engine.startRecording()
    }

    /**
     * Stop recording and return the recorded buffer (or the buffer of given size).
     * WARNING! This may crash if provided with wrong buffer size.
     *
     * @param sizeInBytes buffer size in bytes
     *
     * @return recorded buffer
     */

    fun stopRecording(sizeInBytes: Long): ByteArray {
        val recordedBuffer = engine.stopRecording(sizeInBytes)
        engine.uninitializeRecording()
        return recordedBuffer
    }

    /**
     * Save the recorded buffer to a file.
     *
     * @param path absolute path to the file
     * @param buffer recorded buffer
     */
    fun saveAudioFile(path: String, buffer: ByteArray) {
        engine.saveAudioFile(path, buffer)
    }

    /**
     * Load an audio file and return the buffer.
     *
     * @param path absolute path to the audio file
     * @param fileSize size of the audio file in bytes
     *
     * @return buffer of the audio file
     */
    fun loadAudioFile(path: String, fileSize: Long): ByteArray {
        return engine.loadAudioFile(path, fileSize)
    }
}