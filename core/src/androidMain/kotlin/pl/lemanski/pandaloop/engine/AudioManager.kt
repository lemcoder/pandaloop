package pl.lemanski.pandaloop.engine

internal actual fun getPlaybackDevicesCount(): Int {
    return NativeInterface.Instance.get_playback_devices_count()
}
