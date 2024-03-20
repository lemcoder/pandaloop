#include "miniaudio/miniaudio.h"
#include <stdlib.h>
#include <string.h>
#include "logging.h"
#include "constants.h"
#include <unistd.h>

#ifndef PANDALOOP_AUDIORECORDER_H
#define PANDALOOP_AUDIORECORDER_H

static ma_device device;
static void *recordedBuffer = NULL;
static ma_uint32 recordedFrameCount = 0;

static void capture_data_callback(ma_device *pDevice, void *pOutput, const void *pInput, ma_uint32 frameCount) {
    ma_uint32 bytesPerFrame = ma_get_bytes_per_frame(pDevice->capture.format, pDevice->capture.channels);

    if (recordedBuffer == NULL) {
        recordedBuffer = malloc((recordedFrameCount + frameCount) * bytesPerFrame);
    } else {
        recordedBuffer = realloc(recordedBuffer, (recordedFrameCount + frameCount) * bytesPerFrame);
        if (recordedBuffer == NULL) {
            LOGE("Realloc failed at %d", recordedFrameCount + frameCount);
            return;
        }
    }

    LOGE("Input address: %zu", (intptr_t) pInput);
    ma_copy_pcm_frames(ma_offset_pcm_frames_ptr(recordedBuffer, recordedFrameCount, pDevice->capture.format, pDevice->capture.channels), pInput, frameCount, pDevice->capture.format, pDevice->capture.channels);
    recordedFrameCount += frameCount;
    LOGE("Total frames written: %u", recordedFrameCount);
}

int initializeRecordingDevice() {
    ma_result result;
    ma_device_config deviceConfig;

    deviceConfig = ma_device_config_init(ma_device_type_capture);
    deviceConfig.capture.format = ma_format_f32;
    deviceConfig.capture.channels = CHANNEL_COUNT;
    deviceConfig.noFixedSizedCallback = MA_TRUE;
    deviceConfig.sampleRate = SAMPLE_RATE;
    deviceConfig.dataCallback = capture_data_callback;

    result = ma_device_init(NULL, &deviceConfig, &device);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize capture device.");
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

void uninitalizeRecordingDevice() {
    ma_device_uninit(&device);
}

void *stopRecording() {
    ma_device_stop(&device);
    return recordedBuffer;
}

int startRecording() {
    if (ma_device_start(&device) != MA_SUCCESS) {
        ma_device_uninit(&device);
        printf("Failed to start device.\n");
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

#endif //PANDALOOP_AUDIORECORDER_H