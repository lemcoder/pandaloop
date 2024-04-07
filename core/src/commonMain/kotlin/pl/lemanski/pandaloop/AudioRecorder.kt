package pl.lemanski.pandaloop

expect object AudioRecorder {
    fun initializeRecording(sizeInFrames: Int)
    fun uninitializeRecording()
    fun startRecording()
    fun stopRecording(): ByteArray
}