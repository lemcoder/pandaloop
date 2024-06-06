package pl.lemanski.pandaloop.core.internal.engine

import pl.lemanski.pandaloop.core.engine.jni.PandaLoop

internal actual fun getPlaybackDevicesCount(): Int {
    return PandaLoop.get_playback_devices_count()
}

internal actual fun getBytesPerFrame(channelCount: Int): Int {
    return PandaLoop.get_bytes_per_frame(channelCount)
}