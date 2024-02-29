package pl.lemanski.pandaloop

expect object AudioRecorder {
    fun initializeRecordingDevice(buffer: FloatArray): Int
    fun uninitalizeRecordingDevice(): Int
    fun startRecording(): Int
    fun stopRecording(): Int
}