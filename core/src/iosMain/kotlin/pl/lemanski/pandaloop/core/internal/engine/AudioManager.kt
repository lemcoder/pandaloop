package pl.lemanski.pandaloop.core.internal.engine

import kotlinx.cinterop.*
import pl.lemanski.pandaloop.engine.*

@OptIn(ExperimentalForeignApi::class)
internal actual fun getPlaybackDevicesCount(): Int {
    return get_playback_devices_count()
}


@OptIn(ExperimentalForeignApi::class)
internal actual fun getBytesPerFrame(channelCount: Int): Int {
    return get_bytes_per_frame(channelCount)
}