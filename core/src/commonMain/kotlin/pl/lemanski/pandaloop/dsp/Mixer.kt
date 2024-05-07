package pl.lemanski.pandaloop.dsp

class Mixer {
    fun mixPcmFramesF32(input: FloatArray, output: FloatArray, frameCount: Int, channels: Int, volume: Float): FloatArray {
        if (input.isEmpty() || output.isEmpty() || channels == 0) {
            throw InvalidArgsException()
        }

        if (volume == 0f) {
            return output
        }

        val sampleCount: Int = frameCount * channels

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