#define MINIAUDIO_IMPLEMENTATION

#include "miniaudio/miniaudio.h"
#include <stdlib.h>

#ifndef PANDALOOP_DEVICEMANAGER_H
#define PANDALOOP_DEVICEMANAGER_H

typedef struct {
    ma_uint32 playbackCount;
    ma_uint32 captureCount;
} DeviceInfo;

int getDevicesInfo() {
    return MA_SUCCESS;
}

#endif //PANDALOOP_DEVICEMANAGER_H
