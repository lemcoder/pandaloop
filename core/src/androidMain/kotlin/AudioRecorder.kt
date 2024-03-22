package pl.lemanski.pandaloop

actual object AudioRecorder {
    private var bufferSizeInFrames = 0;
    actual fun initializeRecordingDevice(sizeInFrames: Int) {
        val result = NativeInterface.Instance.initializeRecordingDevice(sizeInFrames)
        if (result != 0) {
            throw RuntimeException("Failed to initialize recording device")
        }
        this.bufferSizeInFrames = sizeInFrames;
    }

    actual fun uninitalizeRecordingDevice(): Int {
        return NativeInterface.Instance.uninitalizeRecordingDevice()
    }

    actual fun startRecording(): Int {
        return NativeInterface.Instance.startRecording()
    }

    actual fun stopRecording(): ByteArray {
        val pointer = NativeInterface.Instance.stopRecording()
        val nativeBufferSize = NativeInterface.Instance.getBytesPerFrame() * this.bufferSizeInFrames
        return pointer.getByteArray(0, nativeBufferSize)
    }
}