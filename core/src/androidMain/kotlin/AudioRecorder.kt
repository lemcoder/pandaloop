package pl.lemanski.pandaloop

actual object AudioRecorder {
    private var bufferSize = 0;
    actual fun initializeRecordingDevice(bufferSize: Int) {
        val result = NativeInterface.Instance.initializeRecordingDevice()
        if (result != 0) {
            throw RuntimeException("Failed to initialize recording device")
        }
        this.bufferSize = bufferSize;
    }

    actual fun uninitalizeRecordingDevice(): Int {
        return NativeInterface.Instance.uninitalizeRecordingDevice()
    }

    actual fun startRecording(): Int {
        return NativeInterface.Instance.startRecording()
    }

    actual fun stopRecording(): ByteArray {
        val pointer = NativeInterface.Instance.stopRecording()
        val nativeBufferSize = NativeInterface.Instance.getBytesPerFrame() * this.bufferSize
        return pointer.getByteArray(0, nativeBufferSize)
    }
}