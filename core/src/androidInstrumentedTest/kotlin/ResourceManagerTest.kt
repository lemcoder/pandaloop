package pl.lemanski.pandaloop

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import java.io.File

class ResourceManagerTest {
    lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun shouldSaveTheBuffer() {
        val path = instrumentationContext.cacheDir.absolutePath + "/sound.wav"
        ResourceManager.saveAudioFile(path, FloatArray(1024) { 500F })

        assert(File(path).exists())
    }
}