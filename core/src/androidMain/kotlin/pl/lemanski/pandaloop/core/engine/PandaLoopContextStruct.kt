package pl.lemanski.pandaloop.core.engine

import pl.lemanski.pandaloop.core.PandaLoopContext

internal class PandaLoopContextStruct(
    @JvmField
    var channelCount: Int = PandaLoopContext.channelCount,
    @JvmField
    var sampleRate: Int = PandaLoopContext.sampleRate
)