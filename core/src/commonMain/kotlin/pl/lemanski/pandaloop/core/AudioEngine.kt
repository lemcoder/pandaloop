package pl.lemanski.pandaloop.core

import pl.lemanski.pandaloop.core.internal.engine.getPlaybackDevicesCount as internalGetPlaybackDevicesCount
import pl.lemanski.pandaloop.core.internal.engine.initializePlaybackDevice as internalInitializePlaybackDevice
import pl.lemanski.pandaloop.core.internal.engine.initializeRecording as internalInitializeRecording
import pl.lemanski.pandaloop.core.internal.engine.loadAudioFile as internalLoadAudioFile
import pl.lemanski.pandaloop.core.internal.engine.saveAudioFile as internalSaveAudioFile
import pl.lemanski.pandaloop.core.internal.engine.setPlaybackBuffer as internalSetPlaybackBuffer
import pl.lemanski.pandaloop.core.internal.engine.startPlayback as internalStartPlayback
import pl.lemanski.pandaloop.core.internal.engine.startRecording as internalStartRecording
import pl.lemanski.pandaloop.core.internal.engine.stopPlayback as internalStopPlayback
import pl.lemanski.pandaloop.core.internal.engine.stopRecording as internalStopRecording
import pl.lemanski.pandaloop.core.internal.engine.uninitializePlaybackDevice as internalUninitializePlaybackDevice
import pl.lemanski.pandaloop.core.internal.engine.uninitializeRecording as internalUninitializeRecording

/**
 * This is an interface that is used to provide the implementation of the audio engine.
 * This can be used to provide a custom implementation of the audio engine (for example for unit testing).
 */
interface AudioEngine {
    interface Options {
        val channelCount: Int
        val sampleRate: Int
        val bytesPerFrame: Int
    }

    val options: Options

    fun getPlaybackDevicesCount(): Int = internalGetPlaybackDevicesCount()
    fun initializePlaybackDevice() = internalInitializePlaybackDevice(options.channelCount, options.sampleRate)
    fun uninitializePlaybackDevice() = internalUninitializePlaybackDevice()
    fun setPlaybackBuffer(buffer: ByteArray) = internalSetPlaybackBuffer(buffer)
    fun startPlayback() = internalStartPlayback()
    fun stopPlayback() = internalStopPlayback()
    fun initializeRecording(sizeInBytes: Long) = internalInitializeRecording(sizeInBytes, options.channelCount, options.sampleRate)
    fun uninitializeRecording() = internalUninitializeRecording()
    fun startRecording() = internalStartRecording()
    fun stopRecording(sizeInBytes: Long): ByteArray = internalStopRecording(sizeInBytes)
    fun saveAudioFile(path: String, buffer: ByteArray) = internalSaveAudioFile(path, buffer, options.channelCount, options.sampleRate)
    fun loadAudioFile(path: String, fileSize: Long): ByteArray = internalLoadAudioFile(path, fileSize)
}