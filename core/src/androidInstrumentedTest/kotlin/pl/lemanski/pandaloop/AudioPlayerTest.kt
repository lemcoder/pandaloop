package pl.lemanski.pandaloop

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.lemanski.pandaloop.engine.initializePlaybackDevice
import pl.lemanski.pandaloop.engine.initializeRecording
import pl.lemanski.pandaloop.engine.mixPlaybackFile
import pl.lemanski.pandaloop.engine.mixPlaybackMemory
import pl.lemanski.pandaloop.engine.startPlayback
import pl.lemanski.pandaloop.engine.startRecording
import pl.lemanski.pandaloop.engine.stopPlayback
import pl.lemanski.pandaloop.engine.stopRecording
import pl.lemanski.pandaloop.engine.uninitializePlaybackDevice

class AudioPlayerTest {
    lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    private fun recordSound(sizeInFrames: Int = 44100): ByteArray {
        initializeRecording(sizeInFrames)
        startRecording()
        Thread.sleep(sizeInFrames / 44100 * 1000L)
        return stopRecording(sizeInFrames)
    }

    @Test
    fun shouldPlayAudioFromBuffer() {
        initializePlaybackDevice()

        println("-----0-----")
        val buffer0 = recordSound()
        mixPlaybackMemory(buffer0, 0)

        Thread.sleep(3000)

        println("-----1-----")
        val buffer1 = recordSound()
        mixPlaybackMemory(buffer1, 1)

        Thread.sleep(3000)

        println("-----2-----")
        val buffer2 = recordSound()
        mixPlaybackMemory(buffer2, 2)

        startPlayback()
        Thread.sleep(20000)
        stopPlayback()
        uninitializePlaybackDevice()
    }

    @Test
    fun shouldPlaySoundFromFile() {
        val path = instrumentationContext.cacheDir.absolutePath + "/test2.wav"
        initializePlaybackDevice()
        mixPlaybackFile(path, 0)
        startPlayback()
        stopPlayback()
        uninitializePlaybackDevice()
    }
}