package pl.lemanski.pandaloop

import org.junit.Test

class AudioManagerTest {
    @Test
    fun shouldGetDevicesCount() {
        val deviceInfo = AudioManager.getPlaybackDevicesCount()
        assert(deviceInfo == 1)
    }
}