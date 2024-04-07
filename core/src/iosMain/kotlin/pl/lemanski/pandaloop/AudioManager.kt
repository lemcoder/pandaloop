package pl.lemanski.pandaloop

import kotlinx.cinterop.*
import pl.lemanski.pandaloop.engine.*

actual object AudioManager {
    @OptIn(ExperimentalForeignApi::class)
    actual fun getPlaybackDevicesCount(): Int {
        return get_playback_devices_count()
    }
}