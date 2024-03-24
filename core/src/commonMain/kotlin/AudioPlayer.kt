package pl.lemanski.pandaloop

expect object AudioPlayer {
    fun initializePlaybackMemory(buffer: ByteArray)
    fun initializePlaybackFile(path: String)
    fun uninitalizePlayback()
    fun startPlayback()
    fun stopPlayback()
}