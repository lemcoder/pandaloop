package pl.lemanski.pandaloop

actual object AudioManager {
    init {
        System.loadLibrary("pl_engine")
    }

    actual fun getPlaybackDevicesCount(): Int {
        return NativeInterface.Instance.getPlaybackDevicesCount()
    }
}