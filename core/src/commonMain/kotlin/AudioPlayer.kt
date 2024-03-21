package pl.lemanski.pandaloop

expect object AudioPlayer {
    fun initializePlaybackDevice(buffer: ByteArray)
    fun uninitalizePlaybackDevice()
    fun startPlayback()
    fun stopPlayback()
}