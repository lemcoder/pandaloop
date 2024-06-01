package pl.lemanski.pandaloop.core

import android.Manifest
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.lemanski.pandaloop.core.internal.engine.initializePlaybackDevice
import pl.lemanski.pandaloop.core.internal.engine.initializeRecording
import pl.lemanski.pandaloop.core.internal.engine.setPlaybackBuffer
import pl.lemanski.pandaloop.core.internal.engine.startPlayback
import pl.lemanski.pandaloop.core.internal.engine.startRecording
import pl.lemanski.pandaloop.core.internal.engine.stopPlayback
import pl.lemanski.pandaloop.core.internal.engine.stopRecording
import pl.lemanski.pandaloop.core.internal.engine.uninitializePlaybackDevice

class AudioPlayerTest {
    private lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.RECORD_AUDIO)

    private fun recordSound(sizeInBytes: Long = 44100 * 4): ByteArray {
        initializeRecording(sizeInBytes)
        startRecording()
        Thread.sleep(sizeInBytes / 44100 * 1000L)
        return stopRecording(sizeInBytes)
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