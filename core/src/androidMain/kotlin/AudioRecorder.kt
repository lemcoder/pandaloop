package pl.lemanski.pandaloop

import com.sun.jna.Memory
import com.sun.jna.Pointer
import com.sun.jna.ptr.PointerByReference

actual object AudioRecorder {
    actual fun initializeRecordingDevice(buffer: FloatArray): Int {
        val nativeMemory = Memory((buffer.size * Float.SIZE_BYTES).toLong())

        for (i in buffer.indices) {
            nativeMemory.setFloat(i * Float.SIZE_BYTES.toLong(), buffer[i])
        }

        val bufferPointer: Pointer = nativeMemory
        return NativeInterface.Instance.initializeRecordingDevice(bufferPointer)
    }

    actual fun uninitalizeRecordingDevice(): Int {
        return NativeInterface.Instance.uninitalizeRecordingDevice()
    }

    actual fun startRecording(): Int {
        return NativeInterface.Instance.startRecording()
    }

    actual fun stopRecording(): Int {
        return NativeInterface.Instance.stopRecording()
    }

    fun getBooty(): Int {
        return NativeInterface.Instance.getSomeBooty()
    }
}