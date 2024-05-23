#include "device_manager.h"

#ifndef PANDALOOP_DEVICEMANAGER_C
#define PANDALOOP_DEVICEMANAGER_C

#define MINIAUDIO_IMPLEMENTATION
#define MA_ENABLE_ONLY_SPECIFIC_BACKENDS
#define MA_NO_RUNTIME_LINKING
#ifdef __ANDROID__
#define MA_ENABLE_OPENSL
#else
#define MA_ENABLE_COREAUDIO
#endif

#include "miniaudio/miniaudio.h"
#include <stdlib.h>

int get_playback_devices_count() {
    ma_context context;
    if (ma_context_init(NULL, 0, NULL, &context) != MA_SUCCESS) {
        return MA_ERROR;
    }

    ma_device_info *pPlaybackInfos;
    ma_uint32 playbackCount;
    ma_device_info *pCaptureInfos;
    ma_uint32 captureCount;
    if (ma_context_get_devices(&context, &pPlaybackInfos, &playbackCount, &pCaptureInfos, &captureCount) != MA_SUCCESS) {
        return MA_ERROR;
    }

    ma_context_uninit(&context);

    return playbackCount;
}

int get_bytes_per_frame(int channelCount) {
    return ma_get_bytes_per_frame(ma_format_f32, channelCount);
}

#endif //PANDALOOP_DEVICEMANAGER_C
