package pl.lemanski.pandaloop.engine

import kotlinx.cinterop.*
import pl.lemanski.pandaloop.engine.*

@OptIn(ExperimentalForeignApi::class)
internal actual fun getPlaybackDevicesCount(): Int {
    return get_playback_devices_count()
}
