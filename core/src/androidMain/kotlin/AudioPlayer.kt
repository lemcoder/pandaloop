package pl.lemanski.pandaloop

actual object AudioPlayer {
    init {
        System.loadLibrary("pl_engine")
    }

    actual fun playC() {
        playSound()
    }

    private external fun playSound()
}