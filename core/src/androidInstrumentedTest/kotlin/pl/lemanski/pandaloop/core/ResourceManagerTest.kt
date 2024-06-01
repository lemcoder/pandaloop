package pl.lemanski.pandaloop.core

import android.Manifest
import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.lemanski.pandaloop.core.internal.engine.initializeRecording
import pl.lemanski.pandaloop.core.internal.engine.saveAudioFile
import pl.lemanski.pandaloop.core.internal.engine.startRecording
import pl.lemanski.pandaloop.core.internal.engine.stopRecording
import pl.lemanski.pandaloop.core.internal.engine.uninitializeRecording
import java.io.File

class ResourceManagerTest {
    lateinit var instrumentationContext: Context

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.RECORD_AUDIO)

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