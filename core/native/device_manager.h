#ifndef PANDALOOP_DEVICEMANAGER_H
#define PANDALOOP_DEVICEMANAGER_H

#include "miniaudio/miniaudio.h"

typedef struct {
    ma_uint32 playbackCount;
    ma_uint32 captureCount;
} DeviceInfo;

int get_playback_devices_count();

#endif // PANDALOOP_DEVICEMANAGER_H
