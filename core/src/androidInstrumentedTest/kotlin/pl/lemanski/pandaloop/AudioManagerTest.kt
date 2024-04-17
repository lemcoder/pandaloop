package pl.lemanski.pandaloop

import org.junit.Test
import pl.lemanski.pandaloop.engine.getPlaybackDevicesCount

class AudioManagerTest {
    @Test
    fun shouldGetDevicesCount() {
        val deviceInfo = getPlaybackDevicesCount()
        assert(deviceInfo == 1)
    }
}