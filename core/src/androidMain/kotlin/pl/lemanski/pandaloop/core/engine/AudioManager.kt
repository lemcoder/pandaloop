package pl.lemanski.pandaloop.core.engine

internal actual fun getPlaybackDevicesCount(): Int {
    return NativeInterface.Instance.get_playback_devices_count()
}

internal actual fun getBytesPerFrame(): Int {
    return NativeInterface.Instance.get_bytes_per_frame()
}