package pl.lemanski.pandaloop

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer

@Suppress("FunctionName")
interface NativeInterface : Library {
    companion object {
        val Instance: NativeInterface = Native.load("pl_engine", NativeInterface::class.java)
    }

    /* device_manager.c */
    fun get_playback_devices_count(): Int

    /* audio_recorder.c */
    fun initialize_recording(sizeInFrames: Int): Int
    fun uninitialize_recording()
    fun start_recording(): Int
    fun stop_recording(): Pointer

    /* audio_player.c */
    fun initialize_playback_device(): Int
    fun mix_playback_memory(buffer: Pointer, sizeInFrames: Int, trackNumber: Int): Int
    fun mix_playback_file(path: Pointer, trackNumber: Int): Int
    fun uninitialize_playback_device()
    fun start_playback(): Int
    fun stop_playback()

    /* resource_manager.c*/
    fun save_audio_file(path: Pointer, buffer: Pointer, bufferSize: Long): Int
    fun get_bytes_per_frame(): Int
}