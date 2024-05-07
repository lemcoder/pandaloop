package pl.lemanski.pandaloop.core.engine

internal actual fun getPlaybackDevicesCount(): Int {
    return NativeInterface.Instance.get_playback_devices_count()
}

internal actual fun getBytesPerFrame(): Int {
    return NativeInterface.Instance.get_bytes_per_frame()
}

internal actual fun getChannelCount(): Int {
    return NativeInterface.Context.channelCount
}

internal actual fun getSampleRate(): Int {
    return NativeInterface.Context.sampleRate
}
