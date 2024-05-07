package pl.lemanski.pandaloop

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.lemanski.pandaloop.core.engine.initializePlaybackDevice
import pl.lemanski.pandaloop.core.engine.initializeRecording
import pl.lemanski.pandaloop.core.engine.setPlaybackBuffer
import pl.lemanski.pandaloop.core.engine.startPlayback
import pl.lemanski.pandaloop.core.engine.startRecording
import pl.lemanski.pandaloop.core.engine.stopPlayback
import pl.lemanski.pandaloop.core.engine.stopRecording
import pl.lemanski.pandaloop.core.engine.uninitializePlaybackDevice

class AudioPlayerTest {
    private lateinit var instrumentationContext: Context

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
        val buffer = recordSound()
        setPlaybackBuffer(buffer)
        startPlayback()
        Thread.sleep(50)
        stopPlayback()
        uninitializePlaybackDevice()
    }
}