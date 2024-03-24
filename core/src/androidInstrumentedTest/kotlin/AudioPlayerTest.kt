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
        AudioRecorder.initializeRecording(441)
        AudioRecorder.startRecording()
        val buffer = AudioRecorder.stopRecording()
        AudioRecorder.uninitalizeRecording()

        AudioPlayer.initializePlaybackMemory(buffer)
        AudioPlayer.startPlayback()
        AudioPlayer.stopPlayback()
    }

    @Test
    fun shouldPlaySoundFromFile() {
        val path = instrumentationContext.cacheDir.absolutePath + "/test2.wav"
        AudioPlayer.initializePlaybackFile(path)
        AudioPlayer.startPlayback()
        AudioPlayer.stopPlayback()
    }
}