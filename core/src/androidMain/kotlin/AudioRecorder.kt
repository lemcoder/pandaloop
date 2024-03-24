package pl.lemanski.pandaloop

actual object AudioRecorder {
    private var bufferSizeInFrames = 0;
    actual fun initializeRecordingDevice(sizeInFrames: Int) {
        val result = NativeInterface.Instance.initialize_recording_device(sizeInFrames)
        if (result != 0) {
            throw RuntimeException("Failed to initialize recording device")
        }
        this.bufferSizeInFrames = sizeInFrames;
    }

    actual fun uninitalizeRecordingDevice(): Int {
        return NativeInterface.Instance.uninitalize_recording_device()
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