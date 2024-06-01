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

internal actual fun initializeRecording(sizeInBytes: Long) {
    val result = initialize_recording(sizeInFrames)
    if (result != 0) {
        throw RuntimeException("Failed to initialize recording device")
    }
}

internal actual fun uninitializeRecording() {
    bufferSizeInFrames = 0
    uninitialize_recording()
}

internal actual fun startRecording() {
    val result = start_recording()
    if (result != 0) {
        throw RuntimeException("Failed to start recording")
    }
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun stopRecording(sizeInBytes: Long): ByteArray {
    val pointer: CPointer<ByteVar> = stop_recording()!!.reinterpret()
    return pointer.readBytes(sizeInBytes.toInt().coerceIn(0, Int.MAX_VALUE))
}