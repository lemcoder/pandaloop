#define MINIAUDIO_IMPLEMENTATION

#include "miniaudio/miniaudio.h"
#include <stdlib.h>

#ifndef PANDALOOP_DEVICEMANAGER_H
#define PANDALOOP_DEVICEMANAGER_H

typedef struct {
    ma_uint32 playbackCount;
    ma_uint32 captureCount;
} DeviceInfo;

int getPlaybackDevicesCount() {
    ma_context context;
    if (ma_context_init(NULL, 0, NULL, &context) != MA_SUCCESS) {
        return MA_ERROR;
    }

    ma_device_info* pPlaybackInfos;
    ma_uint32 playbackCount;
    ma_device_info* pCaptureInfos;
    ma_uint32 captureCount;
    if (ma_context_get_devices(&context, &pPlaybackInfos, &playbackCount, &pCaptureInfos, &captureCount) != MA_SUCCESS) {
        return MA_ERROR;
    }

    ma_context_uninit(&context);

    return playbackCount;
}

#endif //PANDALOOP_DEVICEMANAGER_H
