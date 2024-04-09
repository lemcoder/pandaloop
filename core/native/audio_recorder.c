#include "audio_recorder.h"

#ifndef PANDALOOP_AUDIORECORDER_C
#define PANDALOOP_AUDIORECORDER_C

static ma_device device;
static void *pCaptureBuffer = NULL;
static ma_uint32 recordedFrameCount = 0;
static ma_uint32 requiredSizeInFrames = 0;

static void capture_data_callback(ma_device *pDevice, void *pOutput, const void *pInput, ma_uint32 frameCount) {
    ma_uint32 framesToSave;

    if (recordedFrameCount == requiredSizeInFrames) {
        return;
    }

    if (recordedFrameCount + frameCount > requiredSizeInFrames) {
        framesToSave = requiredSizeInFrames - recordedFrameCount;
    } else {
        framesToSave = frameCount;
    }

    ma_copy_pcm_frames(ma_offset_pcm_frames_ptr(pCaptureBuffer, recordedFrameCount, pDevice->capture.format, pDevice->capture.channels), pInput, frameCount, pDevice->capture.format, pDevice->capture.channels);
    recordedFrameCount += framesToSave;
}

int initialize_recording(int sizeInFrames) {
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

    requiredSizeInFrames = sizeInFrames;
    ma_uint32 bytesPerFrame = ma_get_bytes_per_frame(deviceConfig.capture.format, deviceConfig.capture.channels);
    pCaptureBuffer = malloc(sizeInFrames * bytesPerFrame);
    if (pCaptureBuffer == NULL) {
        LOGE("Failed to allocate memory for buffer.");
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

void uninitialize_recording() {
    ma_device_uninit(&device);
    recordedFrameCount = 0;
    requiredSizeInFrames = 0;
    free(pCaptureBuffer);
    pCaptureBuffer = NULL;
    LOGD("Uninitialized recording");
}

void *stop_recording() {
    ma_device_stop(&device);
    LOGD("Stopped recording");
    return pCaptureBuffer;
}

int start_recording() {
    recordedFrameCount = 0;

    if (pCaptureBuffer == NULL) {
        LOGE("Buffer is null. Call initialize first.");
        return MA_ERROR;
    }

    if (ma_device_start(&device) != MA_SUCCESS) {
        ma_device_uninit(&device);
        LOGE("Failed to start device.\n");
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

#endif //PANDALOOP_AUDIORECORDER_C