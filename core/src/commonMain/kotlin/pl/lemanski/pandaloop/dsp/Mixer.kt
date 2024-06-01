package pl.lemanski.pandaloop.dsp

import pl.lemanski.pandaloop.core.internal.engine.DefaultAudioEngineOptions

class Mixer {
    fun mixPcmFramesF32(input: FloatArray, output: FloatArray, volume: Float): FloatArray {
        if (input.isEmpty() || output.isEmpty()) {
            throw InvalidArgsException("Invalid arguments")
        }

        if (input.size != output.size) {
            throw InvalidArgsException("Input and output must be the same size (provided: in[${input.size}] and out[${output.size}])")
        }

        if (volume == 0f) {
            return output
        }

        val sampleCount: Int = output.size * DefaultAudioEngineOptions.channelCount

        if (volume == 1f) {
            for (i in 0 until sampleCount) {
                output[i] += input[i]
            }
        } else {
            for (i in 0 until sampleCount) {
                output[i] += (input[i] * volume)
            }
        }

        return output
    }
}