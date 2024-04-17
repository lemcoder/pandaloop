package pl.lemanski.pandaloop.engine

import android.Manifest
import androidx.annotation.RequiresPermission

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun initializeRecording(sizeInFrames: Int) {
    val result = NativeInterface.Instance.initialize_recording(sizeInFrames)
    if (result != 0) {
        throw RuntimeException("Failed to initialize recording device")
    }
}

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun uninitializeRecording() {
    NativeInterface.Instance.uninitialize_recording()
}

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun startRecording() {
    val result = NativeInterface.Instance.start_recording()
    if (result != 0) {
        throw RuntimeException("Failed to start recording")
    }
}

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun stopRecording(sizeInFrames: Int): ByteArray {
    val pointer = NativeInterface.Instance.stop_recording()
    val nativeBufferSize = NativeInterface.Instance.get_bytes_per_frame() * sizeInFrames
    return pointer.getByteArray(0, nativeBufferSize)
}
