package pl.lemanski.pandaloop.engine

import kotlinx.cinterop.*
import pl.lemanski.pandaloop.engine.*

@OptIn(ExperimentalForeignApi::class)
internal actual fun getPlaybackDevicesCount(): Int {
    return get_playback_devices_count()
}


@OptIn(ExperimentalForeignApi::class)
internal actual fun getBytesPerFrame(): Int {
    return get_bytes_per_frame()
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun getChannelCount(): Int {
    return get_channel_count()
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun getSampleRate(): Int {
    return get_sample_rate()
}
