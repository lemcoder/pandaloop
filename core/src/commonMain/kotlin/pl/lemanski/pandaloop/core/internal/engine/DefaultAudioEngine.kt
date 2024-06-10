package pl.lemanski.pandaloop.core.internal.engine

import pl.lemanski.pandaloop.core.AudioEngine

internal class DefaultAudioEngine(
    override val options: AudioEngine.Options = DefaultAudioEngineOptions
) : AudioEngine