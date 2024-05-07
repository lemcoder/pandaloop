package pl.lemanski.pandaloop.core.engine

import com.sun.jna.Structure
import com.sun.jna.Structure.FieldOrder
import pl.lemanski.pandaloop.core.PandaLoopContext

@FieldOrder("channelCount", "sampleRate")
internal class PandaLoopContextStruct(
    @JvmField
    var channelCount: Int = PandaLoopContext.channelCount,
    @JvmField
    var sampleRate: Int = PandaLoopContext.sampleRate
) : Structure()