package pl.lemanski.pandaloop

expect object AudioRecorder {
    fun initializeRecording(sizeInFrames: Int)
    fun uninitalizeRecording(): Int
    fun startRecording(): Int
    fun stopRecording(): ByteArray
}