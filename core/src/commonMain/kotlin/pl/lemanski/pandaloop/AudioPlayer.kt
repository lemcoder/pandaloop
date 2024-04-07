package pl.lemanski.pandaloop

expect object AudioPlayer {
    fun initializePlaybackDevice()
    fun uninitializePlaybackDevice()
    fun mixPlaybackMemory(buffer: ByteArray, trackNumber: Int)
    fun mixPlaybackFile(path: String, trackNumber: Int)
    fun startPlayback()
    fun stopPlayback()
}