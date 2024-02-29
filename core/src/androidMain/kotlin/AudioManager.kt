package pl.lemanski.pandaloop

actual object AudioManager {
    actual fun getPlaybackDevicesCount(): Int {
        return NativeInterface.Instance.getPlaybackDevicesCount()
    }
}