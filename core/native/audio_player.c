#include "audio_player.h"

#ifndef PANDALOOP_AUDIOPLAYER_C
#define PANDALOOP_AUDIOPLAYER_C

static pl_audio_buffer buffers[TRACKS];
static ma_device *pPlaybackDevice = NULL;
static float *pPlaybackBuffer = NULL;
static ma_uint64 playbackBufferSizeInFrames = 0;

int pl_audio_buffer_init(float *data, ma_uint64 sizeInFrames, pl_audio_buffer *buffer) {
    if (pPlaybackDevice == NULL) {
        LOGE("Device was not initialized. Call initialize first.");
        return MA_ERROR;
    }
    ma_uint32 bytesPerFrame = ma_get_bytes_per_frame(ma_format_f32, pPlaybackDevice->playback.channels);
    ma_uint64 sizeInBytes = sizeInFrames * bytesPerFrame;
    buffer->data = calloc(sizeInBytes, 1);
    if (buffer->data == NULL) {
        LOGE("Failed to initialize buffer. Out of memory");
        return MA_ERROR;
    }
    memcpy(buffer->data, data, sizeInBytes);
    buffer->sizeInFrames = sizeInFrames;
    buffer->cursor = 0;
    return MA_SUCCESS;
}

void pl_audio_buffer_uninit(pl_audio_buffer *buffer) {
    free(buffer->data);
    buffer->data = NULL;
    buffer->sizeInFrames = 0;
    buffer->cursor = 0;
}

static void playback_data_callback(ma_device *pDevice, void *pOutput, const void *pInput, ma_uint32 frameCount) {
    if (pPlaybackBuffer == NULL) {
        LOGE("Playback buffer is null.");
        return;
    }

    ma_uint32 framesToPlay;
    // TODO check and sync every buffer
    if (buffers[0].cursor == playbackBufferSizeInFrames) {
        buffers[0].cursor = 0; // default looping
    }

    if (buffers[0].cursor + frameCount > playbackBufferSizeInFrames) {
        framesToPlay = playbackBufferSizeInFrames - buffers[0].cursor;
    } else {
        framesToPlay = frameCount;
    }

    ma_copy_pcm_frames(pOutput, ma_offset_pcm_frames_ptr(pPlaybackBuffer, buffers[0].cursor, pDevice->playback.format, pDevice->playback.channels), frameCount, pDevice->playback.format, pDevice->playback.channels);
    buffers[0].cursor += framesToPlay;

    (void) pInput;
}

int initialize_playback_device(pandaloop_context *context) {
    ma_result result;
    ma_device_config deviceConfig;
    pPlaybackDevice = malloc(sizeof(ma_device));
    if (pPlaybackDevice == NULL) {
        LOGE("Failed to allocate memory.");
        return MA_OUT_OF_MEMORY;
    }

    deviceConfig = ma_device_config_init(ma_device_type_playback);
    deviceConfig.playback.format = ma_format_f32;
    deviceConfig.playback.channels = context->channelCount;
    deviceConfig.noFixedSizedCallback = MA_TRUE;
    deviceConfig.sampleRate = context->sampleRate;
    deviceConfig.dataCallback = playback_data_callback;

    result = ma_device_init(NULL, &deviceConfig, pPlaybackDevice);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize playback device.");
        return MA_ERROR;
    }

    return result;
}

int mix_playback_memory(void *buffer, int sizeInFrames, int trackNumber) {
    ma_result result;

    if (pPlaybackDevice == NULL) {
        LOGE("Device was not initialized. Call initialize first.");
        return MA_ERROR;
    }

    for (int i = 0; i < TRACKS; i++) {
        if (buffers[i].sizeInFrames > 0 && buffers[i].sizeInFrames != sizeInFrames) {
            LOGE("Cannot mix buffers of different size");
            return MA_ERROR;
        }
    }

    if (buffers[trackNumber].data != NULL) {
        pl_audio_buffer_uninit(&buffers[trackNumber]);
    }

    result = pl_audio_buffer_init(buffer, sizeInFrames, &buffers[trackNumber]);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize playback memory (%d)", result);
        return result;
    }

    ma_uint32 bytesPerFrame = ma_get_bytes_per_frame(ma_format_f32, pPlaybackDevice->playback.channels);
    ma_uint64 sizeInBytes = sizeInFrames * bytesPerFrame;

    if (pPlaybackBuffer != NULL) {
        free(pPlaybackBuffer);
        pPlaybackBuffer = NULL;
    }

    pPlaybackBuffer = calloc(sizeInBytes, 1);

    if (pPlaybackBuffer == NULL) {
        LOGE("Failed to initialize playback memory");
        return MA_ERROR;
    }

    playbackBufferSizeInFrames = sizeInFrames;

    for (int i = 0; i < TRACKS; i++) {
        if (buffers[i].data != NULL && buffers[i].sizeInFrames == playbackBufferSizeInFrames) {
            result = ma_mix_pcm_frames_f32(pPlaybackBuffer, buffers[i].data, buffers[i].sizeInFrames, pPlaybackDevice->playback.channels, 1);
            if (result != MA_SUCCESS) {
                LOGE("Failed to mix sound (%d)", result);
                return result;
            }
        }
    }

    return result;
}

void uninitialize_playback_device() {
    ma_device_uninit(pPlaybackDevice);
    pPlaybackDevice = NULL;
    free(pPlaybackBuffer);
    pPlaybackBuffer = NULL;
    for (int i = 0; i < TRACKS; i++) {
        pl_audio_buffer_uninit(&buffers[i]);
    }
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