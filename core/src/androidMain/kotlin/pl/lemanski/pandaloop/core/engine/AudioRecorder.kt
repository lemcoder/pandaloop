package pl.lemanski.pandaloop.core.engine

import android.Manifest
import androidx.annotation.RequiresPermission
import pl.lemanski.pandaloop.core.PandaLoopContext
import pl.lemanski.pandaloop.core.engine.jni.PandaLoop

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun initializeRecording(sizeInBytes: Long) {
    val result = PandaLoop.initialize_recording(sizeInBytes, PandaLoopContext.channelCount, PandaLoopContext.sampleRate)
    if (result != 0) {
        throw RuntimeException("Failed to initialize recording device")
    }
}

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun uninitializeRecording() {
    PandaLoop.uninitialize_recording()
}

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun startRecording() {
    val result = PandaLoop.start_recording()
    if (result != 0) {
        throw RuntimeException("Failed to start recording")
    }
}

@RequiresPermission(Manifest.permission.RECORD_AUDIO)
internal actual fun stopRecording(sizeInBytes: Long): ByteArray {
    return PandaLoop.stop_recording(sizeInBytes) ?: byteArrayOf()
}
