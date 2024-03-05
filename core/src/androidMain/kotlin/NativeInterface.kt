package pl.lemanski.pandaloop

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer

interface NativeInterface : Library {
    companion object {
        val Instance = Native.load("pl_engine", NativeInterface::class.java)
    }

    /* device_manager.c */
    fun getPlaybackDevicesCount(): Int
    /* audio_recorder.c */
    fun initializeRecordingDevice(buffer: Pointer): Int
    fun uninitalizeRecordingDevice(): Int
    fun startRecording(): Int
    fun stopRecording(): Int
    /* audio_player.c */
    fun initializePlaybackDevice(buffer: Pointer): Int
    fun uninitalizePlaybackDevice()
    fun startPlayback(): Int
    fun stopPlayback()
}

