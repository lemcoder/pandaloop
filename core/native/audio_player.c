#include "audio_player.h"

#ifndef PANDALOOP_AUDIOPLAYER_C
#define PANDALOOP_AUDIOPLAYER_C

static ma_device *pPlaybackDevice = NULL;
static void *pPlaybackBuffer = NULL;
static ma_uint64 playbackBufferSize = 0;
static ma_uint64 cursor = 0;
static ma_uint32 bytesPerFrame = -1;

static void playback_data_callback(ma_device *pDevice, void *pOutput, const void *pInput, ma_uint32 frameCount) {
    if (pPlaybackBuffer == NULL || playbackBufferSize == 0 || bytesPerFrame == 0) {
        LOGE("Playback buffer is null.");
        return;
    }

    ma_uint32 bytesToPlay;
    ma_uint64 byteCount = frameCount * bytesPerFrame;

    if (cursor >= playbackBufferSize) {
        cursor = 0; // Loop playback by resetting the cursor
    }

    if (cursor + byteCount > playbackBufferSize) {
        bytesToPlay = playbackBufferSize - cursor;
        frameCount = bytesToPlay / bytesPerFrame;
    } else {
        bytesToPlay = byteCount;
    }

    if (bytesToPlay > 0) {
        ma_copy_pcm_frames(pOutput, pPlaybackBuffer + cursor, frameCount, pDevice->playback.format, pDevice->playback.channels);
        cursor += bytesToPlay;
    }

    (void) pInput; // Unused parameter
}

int initialize_playback_device(int channelCount, int sampleRate) {
    ma_result result;
    ma_device_config deviceConfig;
    pPlaybackDevice = malloc(sizeof(ma_device));
    if (pPlaybackDevice == NULL) {
        LOGE("Failed to allocate memory.");
        return MA_OUT_OF_MEMORY;
    }

    deviceConfig = ma_device_config_init(ma_device_type_playback);
    deviceConfig.playback.format = ma_format_f32;
    deviceConfig.playback.channels = channelCount;
    deviceConfig.noFixedSizedCallback = MA_TRUE;
    deviceConfig.sampleRate = sampleRate;
    deviceConfig.dataCallback = playback_data_callback;

    result = ma_device_init(NULL, &deviceConfig, pPlaybackDevice);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize playback device.");
        return MA_ERROR;
    }

    bytesPerFrame = ma_get_bytes_per_frame(deviceConfig.playback.format, deviceConfig.playback.channels);

    return result;
}

int set_playback_buffer(float buffer[], long long int sizeInBytes) {
    if (pPlaybackDevice == NULL) {
        LOGE("Device was not initialized. Call initialize first.");
        return MA_ERROR;
    }

    if (pPlaybackBuffer != NULL) {
        free(pPlaybackBuffer);
        pPlaybackBuffer = NULL;
    }

    if (buffer == NULL) {
        LOGE("Null pointer reference passed in place of buffer");
        return MA_ERROR;
    }

    pPlaybackBuffer = calloc(sizeInBytes, 1);
    if (pPlaybackBuffer == NULL) {
        LOGE("Failed to allocate memory");
        return MA_ERROR;
    }
    memcpy(pPlaybackBuffer, buffer, sizeInBytes);
    playbackBufferSize = sizeInBytes;

    return MA_SUCCESS;
}

void uninitialize_playback_device() {
    ma_device_uninit(pPlaybackDevice);
    pPlaybackDevice = NULL;
    free(pPlaybackBuffer);
    pPlaybackBuffer = NULL;
    LOGD("Playback device uninitialized");
}

int start_playback() {
    if (pPlaybackDevice == NULL) {
        LOGE("Device was not initialized. Call initialize first.");
        return MA_ERROR;
    }

    ma_result result;
    result = ma_device_start(pPlaybackDevice);
    if (result != MA_SUCCESS) {
        LOGE("Failed to start playback.");
    }

    return result;
}

void stop_playback() {
    if (pPlaybackDevice == NULL) {
        LOGE("Device was not initialized. Call initialize first.");
        return;
    }

    ma_device_stop(pPlaybackDevice);
}

#endif // PANDALOOP_AUDIOPLAYER_C