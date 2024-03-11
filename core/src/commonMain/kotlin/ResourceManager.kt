package pl.lemanski.pandaloop

expect object ResourceManager {
    fun saveAudioFile(path: String, buffer: FloatArray)

    fun loadAudioFile(path: String): FloatArray
}