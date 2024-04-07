package pl.lemanski.pandaloop

actual object AudioManager {
    actual fun getPlaybackDevicesCount(): Int {
        return NativeInterface.Instance.get_playback_devices_count()
    }
}