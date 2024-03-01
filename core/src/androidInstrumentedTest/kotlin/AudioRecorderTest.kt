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
        val snapshot = AudioRecorder.getBuffer()
        AudioRecorder.startRecording()

        // wait for a while
        Thread.sleep(1000)

        AudioRecorder.stopRecording()

        assert(!AudioRecorder.getBuffer().contentEquals(snapshot))
    }
}