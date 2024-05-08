package pl.lemanski.pandaloop.dsp

import pl.lemanski.pandaloop.core.PandaLoopContext
import kotlin.math.cos
import kotlin.math.sin

class LowPassFilter(
    private val cutoffHz: Int,
    private val input: FloatArray,
) : Filter {
    private val PI: Float = 3.1415927f
    private val FC: Float = cutoffHz.coerceIn(20, 20_000).toFloat() / PandaLoopContext.sampleRate.toFloat() // Filter cutoff
    private val M: Int = 100 // Filter length
    private val H: FloatArray = FloatArray(101) // Low-pass filter kernel

    init {
        var sum = 0f
        // Calculate filter kernel
        for (i in 0..M) {
            if (i - M / 2 == 0) {
                H[i] = 2 * PI * FC
            } else {
                H[i] = sin(2 * PI * FC * (i - M / 2)) / (i - M / 2)
            }
            H[i] *= (0.54f - 0.46f * cos(PI * i / M)) // Hamming window
            sum += H[i];
        }

        // Normalize the low-pass kernel
        for (i in 0..M) {
            H[i] /= sum
        }
    }

    override fun apply(): FloatArray {
        val output = FloatArray(input.size)
        // Convolve input with filter kernel
        for (j in M until input.size) {
            output[j] = 0f
            for (i in 0..M) {
                output[j] += H[i] * input[j - i]
            }
        }
        return output
    }
}