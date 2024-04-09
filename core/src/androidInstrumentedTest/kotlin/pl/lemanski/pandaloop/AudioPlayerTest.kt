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

    private fun recordSound(sizeInFrames: Int = 44100): ByteArray {
        AudioRecorder.initializeRecording(sizeInFrames)
        AudioRecorder.startRecording()
        Thread.sleep(sizeInFrames / 44100 * 1000L)
        return AudioRecorder.stopRecording()
    }

    @Test
    fun shouldPlayAudioFromBuffer() {
        AudioPlayer.initializePlaybackDevice()

        println("-----0-----")
        val buffer0 = recordSound()
        AudioPlayer.mixPlaybackMemory(buffer0, 0)

        Thread.sleep(3000)

        println("-----1-----")
        val buffer1 = recordSound()
        AudioPlayer.mixPlaybackMemory(buffer1, 1)

        Thread.sleep(3000)

        println("-----2-----")
        val buffer2 = recordSound()
        AudioPlayer.mixPlaybackMemory(buffer2, 2)

        AudioPlayer.startPlayback()
        Thread.sleep(20000)
        AudioPlayer.stopPlayback()
        AudioPlayer.uninitializePlaybackDevice()
    }

    @Test
    fun shouldPlaySoundFromFile() {
        val path = instrumentationContext.cacheDir.absolutePath + "/test2.wav"
        AudioPlayer.initializePlaybackDevice()
        AudioPlayer.mixPlaybackFile(path, 0)
        AudioPlayer.startPlayback()
        AudioPlayer.stopPlayback()
        AudioPlayer.uninitializePlaybackDevice()
    }
}