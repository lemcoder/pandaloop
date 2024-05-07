package pl.lemanski.pandaloop.dsp

internal interface Filter {
    fun apply() : FloatArray
}