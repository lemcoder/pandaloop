package pl.lemanski.pandaloop

expect object ResourceManager {
    fun saveAudioFile(path: String, buffer: ByteArray)

    fun loadAudioFile(path: String): ByteArray
}