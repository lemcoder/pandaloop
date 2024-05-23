package pl.lemanski.pandaloop.core.engine

import pl.lemanski.pandaloop.core.engine.jni.PandaloopCore
import pl.lemanski.pandaloop.core.engine.jni.SWIGTYPE_p_ma_uint64
import pl.lemanski.pandaloop.core.engine.jni.SWIGTYPE_p_pandaloop_context

internal interface NativeInterface {
    companion object {
        val library = System.loadLibrary("pl_engine")
        val Instance = object : NativeInterface { }
    }

    /* device_manager.c */
    fun get_playback_devices_count(): Int = PandaloopCore.get_playback_devices_count()
    fun get_bytes_per_frame(context: PandaLoopContextStruct = PandaLoopContextStruct()): Int = PandaloopCore.get_bytes_per_frame()

    /* audio_recorder.c */
    fun initialize_recording(sizeInBytes: Long, context: PandaLoopContextStruct = PandaLoopContextStruct()): Int =
        PandaloopCore.initialize_recording(SWIGTYPE_p_ma_uint64(sizeInBytes, false), SWIGTYPE_p_pandaloop_context(context))
    fun uninitialize_recording() = PandaloopCore.uninitialize_recording()
    fun start_recording(): Int = PandaloopCore.start_recording()
    fun stop_recording() = PandaloopCore.stop_recording()

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