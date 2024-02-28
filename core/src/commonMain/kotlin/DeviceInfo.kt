package pl.lemanski.pandaloop

import com.sun.jna.Structure

class DeviceInfo : Structure() {
    var playbackCount: Int = 0
    var captureCount: Int = 0
    override fun getFieldOrder(): List<String> {
        return listOf("id", "name", "isDefault")
    }
}
