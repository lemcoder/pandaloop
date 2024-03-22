package pl.lemanski.pandaloop

expect object AudioRecorder {
    fun initializeRecordingDevice(sizeInFrames: Int)
    fun uninitalizeRecordingDevice(): Int
    fun startRecording(): Int
    fun stopRecording(): ByteArray
}