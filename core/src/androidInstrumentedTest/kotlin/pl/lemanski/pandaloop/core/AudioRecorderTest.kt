package pl.lemanski.pandaloop.core

import android.Manifest
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import pl.lemanski.pandaloop.core.internal.engine.initializeRecording
import pl.lemanski.pandaloop.core.internal.engine.startRecording
import pl.lemanski.pandaloop.core.internal.engine.stopRecording

class AudioRecorderTest {

    @get:Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.RECORD_AUDIO)

    @Test
    fun shouldWriteAudioToBuffer() {
        initializeRecording(441)
        startRecording()
        val buffer = stopRecording(441)
        assert(buffer.isNotEmpty())
    }
}