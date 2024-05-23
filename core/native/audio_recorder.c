#include "audio_recorder.h"
#include <jni.h>

#ifndef PANDALOOP_AUDIORECORDER_C
#define PANDALOOP_AUDIORECORDER_C

static ma_device device;
static void *pCaptureBuffer = NULL;
static ma_uint32 recordedBytes = 0;
static ma_uint32 requiredSizeBytes = -1;
static int bytesPerFrame = -1;

static void capture_data_callback(ma_device *pDevice, void *pOutput, const void *pInput, ma_uint32 frameCount) {
    if (pCaptureBuffer == NULL || requiredSizeBytes == -1 || bytesPerFrame == -1) {
        LOGE("Recording not initialized. Call initialize first");
        return;
    }

    ma_uint32 bytesToSave;
    ma_uint32 byteCount = bytesPerFrame * frameCount;

    if (recordedBytes >= requiredSizeBytes) {
        return;
    }

    if (recordedBytes + byteCount > requiredSizeBytes) {
        bytesToSave = requiredSizeBytes - recordedBytes;
        // Adjust frameCount to fit the remaining buffer size
        frameCount = bytesToSave / bytesPerFrame;
    } else {
        bytesToSave = byteCount;
    }

    ma_copy_pcm_frames((ma_uint8 *) pCaptureBuffer + recordedBytes, pInput, frameCount, pDevice->capture.format, pDevice->capture.channels);
    recordedBytes += bytesToSave;

    (void) pOutput;
}

int initialize_recording(long long int sizeInBytes, int channelCount, int sampleRate) {
    ma_result result;
    ma_device_config deviceConfig;

    deviceConfig = ma_device_config_init(ma_device_type_capture);
    deviceConfig.capture.format = ma_format_f32;
    deviceConfig.capture.channels = channelCount;
    deviceConfig.noFixedSizedCallback = MA_TRUE;
    deviceConfig.sampleRate = sampleRate;
    deviceConfig.dataCallback = capture_data_callback;

    result = ma_device_init(NULL, &deviceConfig, &device);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize capture device.");
        return MA_ERROR;
    }

    requiredSizeBytes = sizeInBytes;
    bytesPerFrame = ma_get_bytes_per_frame(ma_format_f32, channelCount);
    pCaptureBuffer = calloc(sizeInBytes, 1);

    if (pCaptureBuffer == NULL) {
        LOGE("Failed to allocate memory for buffer.");
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

void uninitialize_recording() {
    ma_device_uninit(&device);
    recordedBytes = 0;
    requiredSizeBytes = 0;
    free(pCaptureBuffer);
    pCaptureBuffer = NULL;
    LOGD("Uninitialized recording");
}

float *stop_recording(long long int sizeInBytes) {
    ma_device_stop(&device);
    LOGD("Stopped recording");

    float * tmpBuffer = malloc(sizeInBytes);
    memcpy(tmpBuffer, pCaptureBuffer, sizeInBytes);

    return tmpBuffer;
}

int start_recording() {
    recordedBytes = 0;

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












