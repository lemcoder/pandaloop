package pl.lemanski.pandaloop

import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test

class AudioRecorderTest {

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    @Test
    fun shouldWriteAudioToBuffer() {
        val buffer = FloatArray(1024)
        val snapshot = buffer
        AudioRecorder.initializeRecordingDevice(buffer)
        AudioRecorder.startRecording()

        // wait for a while
        for (i in 0..100_000)
        {
            ;
        }
        AudioRecorder.stopRecording()

        assert(!buffer.contentEquals(snapshot))
    }
}