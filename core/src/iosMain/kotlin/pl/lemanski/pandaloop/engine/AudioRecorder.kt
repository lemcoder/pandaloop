package pl.lemanski.pandaloop.engine

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.reinterpret
import pl.lemanski.pandaloop.engine.get_bytes_per_frame
import pl.lemanski.pandaloop.engine.initialize_recording
import pl.lemanski.pandaloop.engine.start_recording
import pl.lemanski.pandaloop.engine.stop_recording
import pl.lemanski.pandaloop.engine.uninitialize_recording

internal actual fun initializeRecording(sizeInFrames: Int) {
    val result = initialize_recording(sizeInFrames)
    if (result != 0) {
        throw RuntimeException("Failed to initialize recording device")
    }
    this.bufferSizeInFrames = sizeInFrames
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

internal actual fun stopRecording(sizeInFrames: Int): ByteArray {
    val pointer: CPointer<ByteVar> = stop_recording()!!.reinterpret()
    val sizeInBytes = bufferSizeInFrames * get_bytes_per_frame()
    return pointer.readBytes(sizeInBytes)
}