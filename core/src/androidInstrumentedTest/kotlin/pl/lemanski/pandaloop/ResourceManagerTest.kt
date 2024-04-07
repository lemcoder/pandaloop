package pl.lemanski.pandaloop

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File

class ResourceManagerTest {
    lateinit var instrumentationContext: Context

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun shouldSaveTheBuffer() {
        val path = instrumentationContext.cacheDir.absolutePath + "/test2.wav"
        if (File(path).exists()) {
            File(path).delete()
        }

        AudioRecorder.initializeRecording(441)
        AudioRecorder.startRecording()
        val recordedBuffer = AudioRecorder.stopRecording()

        // Save the file
        ResourceManager.saveAudioFile(path, recordedBuffer)
        AudioRecorder.uninitializeRecording()

        assert(File(path).exists())
    }
}