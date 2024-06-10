package pl.lemanski.pandaloop.core.internal.engine

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.reinterpret
import pl.lemanski.pandaloop.engine.initialize_recording
import pl.lemanski.pandaloop.engine.start_recording
import pl.lemanski.pandaloop.engine.stop_recording
import pl.lemanski.pandaloop.engine.uninitialize_recording

@OptIn(ExperimentalForeignApi::class)
internal actual fun initializeRecording(sizeInBytes: Long, channelCount: Int, sampleRate: Int) {
    val result = initialize_recording(sizeInBytes, channelCount, sampleRate)
    if (result != 0) {
        throw RuntimeException("Failed to initialize recording device")
    }
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun uninitializeRecording() {
    uninitialize_recording()
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun startRecording() {
    val result = start_recording()
    if (result != 0) {
        throw RuntimeException("Failed to start recording")
    }
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun stopRecording(sizeInBytes: Long): ByteArray {
    val pointer: CPointer<ByteVar> = stop_recording(sizeInBytes)!!.reinterpret()
    return pointer.readBytes(sizeInBytes.toInt().coerceIn(0, Int.MAX_VALUE))
}