package pl.lemanski.pandaloop.engine

internal expect fun getPlaybackDevicesCount(): Int

internal expect fun getBytesPerFrame(): Int

internal expect fun getChannelCount(): Int

internal expect fun getSampleRate(): Int