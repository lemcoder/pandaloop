package pl.lemanski.pandaloop

import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test

class AudioRecorderTest {

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    @Test
    fun shouldWriteAudioToBuffer() {
        AudioRecorder.initializeRecordingDevice(1024)
        AudioRecorder.startRecording()

        // wait for a while
        Thread.sleep(1000)

        val buffer = AudioRecorder.stopRecording()
    }
}