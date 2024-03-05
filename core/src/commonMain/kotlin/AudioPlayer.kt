package pl.lemanski.pandaloop

expect object AudioPlayer {
    fun initializePlaybackDevice(buffer: FloatArray)
    fun uninitalizePlaybackDevice()
    fun startPlayback()
    fun stopPlayback()
}