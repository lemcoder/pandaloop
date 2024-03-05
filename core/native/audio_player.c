#include <string.h>
#include "miniaudio/miniaudio.h."
#include "logging.h"
#include "constants.h"

#ifndef PANDALOOP_AUDIOPLAYER_H
#define PANDALOOP_AUDIOPLAYER_H

#include "miniaudio/miniaudio.h"

static ma_device device;
static ma_context context;

static void data_callback(ma_device *pDevice, void *pOutput, const void *pInput,
                            ma_uint32 frameCount) {
    float *buffer = (float *) pDevice->pUserData;
    memcpy(pOutput, buffer, frameCount * CHANNEL_COUNT * sizeof(float));
}

int initializePlaybackDevice(float *buffer) {
    ma_result result;

    result = ma_context_init(NULL, 0, NULL, &context);
    if (result != MA_SUCCESS) {
        LOGD("Failed to initialize miniaudio context.\n");
        return MA_ERROR;
    }

    ma_device_config deviceConfig = ma_device_config_init(ma_device_type_playback);
    deviceConfig.playback.format = ma_format_f32;
    deviceConfig.playback.channels = CHANNEL_COUNT;
    deviceConfig.sampleRate = SAMPLE_RATE;
    deviceConfig.dataCallback = data_callback;
    deviceConfig.pUserData = buffer;

    result = ma_device_init(&context, &deviceConfig, &device);
    if (result != MA_SUCCESS) {
        LOGD("Failed to initialize playback device.\n");
        ma_context_uninit(&context);
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

void uninitalizePlaybackDevice() {
    ma_device_uninit(&device);
    ma_context_uninit(&context);
}

int startPlayback() {
    ma_result result;
    result = ma_device_start(&device);
    if (result != MA_SUCCESS) {
        LOGD("Failed to start playback.\n");
        ma_device_uninit(&device);
        ma_context_uninit(&context);
        return MA_ERROR;
    }

    LOGD("Playback started. \n");

    return MA_SUCCESS;
}

void stopPlayback() {
    ma_device_stop(&device);
}

#endif // PANDALOOP_AUDIOPLAYER_H
