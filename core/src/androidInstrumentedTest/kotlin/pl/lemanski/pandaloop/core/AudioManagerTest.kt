package pl.lemanski.pandaloop.core

import org.junit.Test
import pl.lemanski.pandaloop.core.engine.getPlaybackDevicesCount

class AudioManagerTest {
    @Test
    fun shouldGetDevicesCount() {
        val deviceInfo = getPlaybackDevicesCount()
        assert(deviceInfo == 1)
    }
}