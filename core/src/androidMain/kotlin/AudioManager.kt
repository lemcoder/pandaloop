package pl.lemanski.pandaloop

import com.sun.jna.ptr.PointerByReference

actual object AudioManager {
    init {
        System.loadLibrary("pl_engine")
    }

    actual fun getDevicesInfo(): Int {
        return NativeInterface.Instance.getDevicesInfo()
    }
}