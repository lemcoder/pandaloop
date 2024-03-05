#include "miniaudio/miniaudio.h"
#include <stdlib.h>
#include <string.h>
#include "logging.h"
#include "constants.h"

#ifndef PANDALOOP_AUDIORECORDER_H
#define PANDALOOP_AUDIORECORDER_H

static ma_device device;
static ma_context context;

static void data_callback(ma_device* pDevice, void* pOutput, const void* pInput, ma_uint32 frameCount)
{
    float* buffer = (float*)pDevice->pUserData;
    memcpy(buffer, pInput, frameCount * CHANNEL_COUNT * sizeof(float));
}

int initializeRecordingDevice(float* buffer)
{
    ma_result result;

    result = ma_context_init(NULL, 0, NULL, &context);

    if (result != MA_SUCCESS) {
        LOGD("Failed to initialize miniaudio context.\n");
        return MA_ERROR;
    }

    ma_device_config deviceConfig = ma_device_config_init(ma_device_type_capture);
    deviceConfig.capture.format = ma_format_f32;
    deviceConfig.capture.channels = CHANNEL_COUNT;
    deviceConfig.sampleRate = SAMPLE_RATE;
    deviceConfig.dataCallback = data_callback;
    deviceConfig.pUserData = buffer;

    result = ma_device_init(&context, &deviceConfig, &device);
    if (result != MA_SUCCESS) {
        LOGD("Failed to initialize recording device.\n");
        ma_context_uninit(&context);
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

void uninitalizeRecordingDevice()
{
    ma_device_uninit(&device);
    ma_context_uninit(&context);
}

void stopRecording()
{
    ma_device_stop(&device);
}

int startRecording()
{
    ma_result result;
    result = ma_device_start(&device);
    if (result != MA_SUCCESS) {
        LOGD("Failed to start recording.\n");
        ma_device_uninit(&device);
        ma_context_uninit(&context);
        return MA_ERROR;
    }

    LOGD("Recording started. \n");

    return MA_SUCCESS;
}

#endif //PANDALOOP_AUDIOPLAYER_H