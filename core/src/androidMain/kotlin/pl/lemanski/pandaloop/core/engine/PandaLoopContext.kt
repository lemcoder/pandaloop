package pl.lemanski.pandaloop.core.engine

import com.sun.jna.Structure
import com.sun.jna.Structure.FieldOrder

@FieldOrder("channelCount", "sampleRate")
class PandaLoopContext(
    @JvmField
    var channelCount: Int = 1,
    @JvmField
    var sampleRate: Int = 44_100
) : Structure()