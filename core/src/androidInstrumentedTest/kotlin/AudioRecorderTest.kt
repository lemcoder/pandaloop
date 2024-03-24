package pl.lemanski.pandaloop

import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test

class AudioRecorderTest {

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    @Test
    fun shouldWriteAudioToBuffer() {
        AudioRecorder.initializeRecording(441)
        AudioRecorder.startRecording()
        val buffer = AudioRecorder.stopRecording()
        assert(buffer.isNotEmpty())
    }
}