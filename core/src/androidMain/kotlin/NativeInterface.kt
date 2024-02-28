package pl.lemanski.pandaloop

import com.sun.jna.Library
import com.sun.jna.Native

interface NativeInterface : Library {
    companion object {
        val Instance = Native.load("pl_engine", NativeInterface::class.java)
    }

    fun getPlaybackDevicesCount(): Int
}

