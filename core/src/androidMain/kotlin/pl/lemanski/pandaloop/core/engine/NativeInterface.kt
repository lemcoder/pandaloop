package pl.lemanski.pandaloop.core.engine

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer

@Suppress("FunctionName")
internal interface NativeInterface : Library {
    companion object {
        val Instance: NativeInterface = Native.load("pl_engine", NativeInterface::class.java)
    }

    /* device_manager.c */
    fun get_playback_devices_count(): Int
    fun get_bytes_per_frame(context: PandaLoopContextStruct = PandaLoopContextStruct()): Int

    /* audio_recorder.c */
    fun initialize_recording(sizeInBytes: Long, context: PandaLoopContextStruct = PandaLoopContextStruct()): Int
    fun uninitialize_recording()
    fun start_recording(): Int
    fun stop_recording(): Pointer

    /* audio_player.c */
    fun initialize_playback_device(context: PandaLoopContextStruct = PandaLoopContextStruct()): Int
    fun set_playback_buffer(buffer: Pointer, sizeInBytes: Long): Int
    fun uninitialize_playback_device()
    fun start_playback(): Int
    fun stop_playback()

    /* resource_manager.c*/
    fun save_audio_file(pFilePath: Pointer, pBuffer: Pointer, bufferSize: Long, context: PandaLoopContextStruct = PandaLoopContextStruct()): Int
    fun load_audio_file(pFilePath: Pointer, pBufferSize: Pointer, context: PandaLoopContextStruct = PandaLoopContextStruct()): Pointer
}