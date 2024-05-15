package pl.lemanski.pandaloop.core.engine

import android.Manifest
import androidx.annotation.RequiresPermission

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun initializeRecording(sizeInBytes: Long) {
    val result = NativeInterface.Instance.initialize_recording(sizeInBytes)
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
internal actual fun stopRecording(sizeInBytes: Long): ByteArray {
    val pointer = NativeInterface.Instance.stop_recording()
    return pointer.getByteArray(0, sizeInBytes.toInt().coerceAtLeast(0))
}
