package pl.lemanski.pandaloop

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer

interface NativeInterface : Library {
    companion object {
        val Instance = Native.load("pl_engine", NativeInterface::class.java)
    }

    fun getPlaybackDevicesCount(): Int

    fun initializeRecordingDevice(buffer: Pointer): Int

    fun uninitalizeRecordingDevice(): Int

    fun startRecording(): Int

    fun stopRecording(): Int

    fun getSomeBooty(): Int
}

