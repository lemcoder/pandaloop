package pl.lemanski.pandaloop

actual object AudioRecorder {
    private var bufferSizeInFrames = 0
    actual fun initializeRecording(sizeInFrames: Int) {
        val result = NativeInterface.Instance.initialize_recording(sizeInFrames)
        if (result != 0) {
            throw RuntimeException("Failed to initialize recording device")
        }
        this.bufferSizeInFrames = sizeInFrames
    }

    actual fun uninitializeRecording() {
        bufferSizeInFrames = 0
        NativeInterface.Instance.uninitialize_recording()
    }

    actual fun startRecording() {
        val result = NativeInterface.Instance.start_recording()
        if (result != 0) {
            throw RuntimeException("Failed to start recording")
        }
    }

    actual fun stopRecording(): ByteArray {
        val pointer = NativeInterface.Instance.stop_recording()
        val nativeBufferSize = NativeInterface.Instance.get_bytes_per_frame() * this.bufferSizeInFrames
        return pointer.getByteArray(0, nativeBufferSize)
    }
}