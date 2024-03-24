package pl.lemanski.pandaloop

actual object AudioRecorder {
    private var bufferSizeInFrames = 0;
    actual fun initializeRecording(sizeInFrames: Int) {
        val result = NativeInterface.Instance.initialize_recording(sizeInFrames)
        if (result != 0) {
            throw RuntimeException("Failed to initialize recording device")
        }
        this.bufferSizeInFrames = sizeInFrames;
    }

    actual fun uninitalizeRecording(): Int {
        return NativeInterface.Instance.uninitalize_recording()
    }

    actual fun startRecording(): Int {
        return NativeInterface.Instance.start_recording()
    }

    actual fun stopRecording(): ByteArray {
        val pointer = NativeInterface.Instance.stop_recording()
        val nativeBufferSize = NativeInterface.Instance.get_bytes_per_frame() * this.bufferSizeInFrames
        return pointer.getByteArray(0, nativeBufferSize)
    }
}