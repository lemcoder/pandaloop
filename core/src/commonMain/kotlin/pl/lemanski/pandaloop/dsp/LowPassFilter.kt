package pl.lemanski.pandaloop.dsp

import pl.lemanski.pandaloop.core.PandaLoopContext
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class LowPassFilter(
    private val cutoffHz: Int,
    private val input: FloatArray,
) : Filter {
    override fun apply(): FloatArray {
        val output = FloatArray(input.size)
        val filterLength = 101 // Filter length
        val filterKernel = FloatArray(filterLength)

        val fc = cutoffHz / PandaLoopContext.sampleRate // Sampling frequency assumed to be 44100 Hz - filter cutoff in (0 - .5)
        val pi = PI.toFloat()

        // Calculate filter kernel
        val sum = filterKernel.indices.sumOf { i ->
            val n = i - filterLength / 2
            if (n == 0) {
                filterKernel[i] = 2 * pi * fc
            } else {
                filterKernel[i] = sin(2 * pi * fc * n) / n
            }
            filterKernel[i] *= (0.54f - 0.46f * cos(pi * i / filterLength.toFloat())) // Hamming window
            filterKernel[i].toDouble()
        }.toFloat()

        // Normalize filter kernel
        for (i in filterKernel.indices) {
            filterKernel[i] /= sum
        }

        // Apply filter
        for (j in input.indices) {
            var filteredValue = 0f
            for (i in 0 until filterLength) {
                if (j - i >= 0) {
                    filteredValue += (filterKernel[i] * input[j - i])
                }
            }
            output[j] = filteredValue
        }

        return output
    }
}