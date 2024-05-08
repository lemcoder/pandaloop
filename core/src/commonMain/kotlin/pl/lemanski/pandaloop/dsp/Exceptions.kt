package pl.lemanski.pandaloop.dsp

class ProcessingException : Throwable()

class InvalidArgsException(override val message: String) : Throwable()