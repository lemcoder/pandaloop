package pl.lemanski.pandaloop

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.lemanski.pandaloop.core.engine.initializeRecording
import pl.lemanski.pandaloop.core.engine.saveAudioFile
import pl.lemanski.pandaloop.core.engine.startRecording
import pl.lemanski.pandaloop.core.engine.stopRecording
import pl.lemanski.pandaloop.core.engine.uninitializeRecording
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

        initializeRecording(441)
        startRecording()
        val recordedBuffer = stopRecording(441)

        // Save the file
        saveAudioFile(path, recordedBuffer)
        uninitializeRecording()

        assert(File(path).exists())
    }
}