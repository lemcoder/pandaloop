package pl.lemanski.pandaloop

import org.junit.Test

class AudioManagerTest {
    @Test
    fun shouldGetDevicesCount() {
        val deviceInfo = AudioManager.getDevicesInfo()
        assert(deviceInfo == 0)
    }
}
