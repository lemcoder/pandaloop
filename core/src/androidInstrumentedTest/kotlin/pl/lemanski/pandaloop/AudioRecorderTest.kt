package pl.lemanski.pandaloop

import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import pl.lemanski.pandaloop.core.engine.initializeRecording
import pl.lemanski.pandaloop.core.engine.startRecording
import pl.lemanski.pandaloop.core.engine.stopRecording

class AudioRecorderTest {

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    @Test
    fun shouldWriteAudioToBuffer() {
        initializeRecording(441)
        startRecording()
        val buffer = stopRecording(441)
        assert(buffer.isNotEmpty())
    }
}