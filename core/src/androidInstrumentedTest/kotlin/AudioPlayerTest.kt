package pl.lemanski.pandaloop

import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test

class AudioPlayerTest {
    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    @Test
    fun shouldPlayAudioFromBuffer() {
        val buffer = FloatArray(1024) { 500f }
        AudioPlayer.initializePlaybackDevice(buffer)
        AudioPlayer.startPlayback()

        // wait for a while
        Thread.sleep(1000)

        AudioPlayer.stopPlayback()
    }

}