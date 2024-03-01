package pl.lemanski.pandaloop

import com.sun.jna.Memory
import com.sun.jna.Native

actual object AudioRecorder {
    private var recordingBufferSize: Int = 0
    private lateinit var recordingBuffer: Memory

    actual fun initializeRecordingDevice(bufferSize: Long) {
        // Allocate memory for the buffer
        val memory = Memory(Native.getNativeSize(Float::class.java) * bufferSize)

        // Call native method passing the Memory pointer
        val result = NativeInterface.Instance.initializeRecordingDevice(memory)
        if (result != 0) {
            throw RuntimeException("Failed to initialize recording device")
        }

        recordingBufferSize = bufferSize.toInt()
        recordingBuffer = memory // assign memory for buffer
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

    fun getBuffer(): FloatArray {
        return recordingBuffer.getFloatArray(0, recordingBufferSize)
    }
}