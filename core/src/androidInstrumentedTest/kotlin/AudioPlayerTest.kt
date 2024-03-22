package pl.lemanski.pandaloop

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AudioPlayerTest {
    lateinit var instrumentationContext: Context
    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    @Test
    fun shouldPlayAudioFromBuffer() {
        AudioRecorder.initializeRecordingDevice(88200)
        AudioRecorder.startRecording()
        Thread.sleep(2200)
        val buffer = AudioRecorder.stopRecording()
        AudioRecorder.uninitalizeRecordingDevice()

        AudioPlayer.initializePlaybackDevice(buffer)
        AudioPlayer.startPlayback()

        // wait for a while
        Thread.sleep(2000)

        AudioPlayer.stopPlayback()
    }

}