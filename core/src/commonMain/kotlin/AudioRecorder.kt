package pl.lemanski.pandaloop

expect object AudioRecorder {
    fun initializeRecordingDevice(bufferSize: Long)
    fun uninitalizeRecordingDevice(): Int
    fun startRecording(): Int
    fun stopRecording(): Int
}