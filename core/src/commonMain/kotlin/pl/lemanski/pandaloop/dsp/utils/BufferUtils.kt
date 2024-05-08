package pl.lemanski.pandaloop.dsp.utils

import com.ditchoom.buffer.ByteOrder
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.allocate
import com.ditchoom.buffer.wrap

fun ByteArray.toFloatArray(): FloatArray {
    val buffer = PlatformBuffer.wrap(
        array = this,
        byteOrder = ByteOrder.LITTLE_ENDIAN
    )

    buffer.resetForRead()
    return FloatArray(this.size / Float.SIZE_BYTES).map { buffer.readFloat() }.toFloatArray()
}

fun FloatArray.toByteArray(): ByteArray {
    val buffer = PlatformBuffer.allocate(
        size = this.size * Float.SIZE_BYTES,
        byteOrder = ByteOrder.LITTLE_ENDIAN
    )
    buffer.resetForWrite()
    this.forEach { buffer.writeFloat(it) }
    buffer.resetForRead()

    return buffer.readByteArray(this.size * Float.SIZE_BYTES)
}