#include <string.h>
#include <malloc.h>
#include "miniaudio/miniaudio.h."
#include "logging.h"
#include "constants.h"

#ifndef PANDALOOP_AUDIOPLAYER_H
#define PANDALOOP_AUDIOPLAYER_H

#include "miniaudio/miniaudio.h"

#define TRACKS 4

typedef struct {
    float *data;
    ma_uint64 sizeInFrames;
    ma_uint64 cursor;
} pl_audio_buffer;

int pl_audio_buffer_init(float *data, ma_uint64 sizeInFrames, pl_audio_buffer *buffer) {
    ma_uint32 bytesPerFrame = ma_get_bytes_per_frame(ma_format_f32, CHANNEL_COUNT);
    ma_uint64 sizeInBytes = sizeInFrames * bytesPerFrame;
    buffer->data = malloc(sizeInBytes);
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
    buffer->sizeInFrames = 0;
    buffer->cursor = 0;
}

// Memory playback variables
static ma_device pPlaybackDevice;
static pl_audio_buffer buffers[TRACKS];
static float *pPlaybackBuffer = NULL;
static ma_uint64 playbackBufferSizeInFrames = 0;

static void playback_data_callback(ma_device *pDevice, void *pOutput, const void *pInput, ma_uint32 frameCount) {
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

int initialize_playback_device() {
    ma_result result;
    ma_device_config deviceConfig;

    deviceConfig = ma_device_config_init(ma_device_type_playback);
    deviceConfig.playback.format = ma_format_f32;
    deviceConfig.playback.channels = CHANNEL_COUNT;
    deviceConfig.noFixedSizedCallback = MA_TRUE;
    deviceConfig.sampleRate = SAMPLE_RATE;
    deviceConfig.dataCallback = playback_data_callback;

    result = ma_device_init(NULL, &deviceConfig, &pPlaybackDevice);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize playback device.");
    }

    return result;
}

int mix_playback_memory(void *buffer, int sizeInFrames, int trackNumber) {
    ma_result result;

    result = pl_audio_buffer_init(buffer, sizeInFrames, &buffers[trackNumber]);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize playback memory (%d)", result);
        return result;
    }

    if (pPlaybackBuffer == NULL) {
        ma_uint32 bytesPerFrame = ma_get_bytes_per_frame(ma_format_f32, CHANNEL_COUNT);
        ma_uint64 sizeInBytes = sizeInFrames * bytesPerFrame;
        pPlaybackBuffer = malloc(sizeInBytes);
        if (pPlaybackBuffer == NULL) {
            LOGE("Failed to initialize playback memory");
            return MA_ERROR;
        }
        playbackBufferSizeInFrames = sizeInFrames;
    }

    result = ma_mix_pcm_frames_f32(pPlaybackBuffer, buffers[trackNumber].data, buffers[trackNumber].sizeInFrames, CHANNEL_COUNT, 1);
    if (result != MA_SUCCESS) {
        LOGE("Failed to mix sound (%d)", result);
    }

    return result;
}

int mix_playback_file(char *path, int trackNumber) {
    ma_result result;
    ma_decoder decoder;
    ma_uint64 framesAvailable = 0;
    ma_uint64 framesRead = 0;
    void *tempBuffer = NULL;

    result = ma_decoder_init_file(path, NULL, &decoder);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize decoder: %d", result);
        return MA_ERROR;
    }

    ma_decoder_get_available_frames(&decoder, &framesAvailable);
    tempBuffer = malloc(framesAvailable);
    if (tempBuffer == NULL) {
        LOGD("Failed to initialize buffer. Out of memory");
        return MA_ERROR;
    }

    while (framesRead < framesAvailable) {
        ma_decoder_read_pcm_frames(&decoder, tempBuffer, 441, &framesRead);
    }

    result = mix_playback_memory(tempBuffer, framesAvailable, trackNumber);

    return result;
}

void uninitalize_playback_device() {
    ma_device_uninit(&pPlaybackDevice);
    free(pPlaybackBuffer);
    for (int i = 0; i < TRACKS; i++) {
        pl_audio_buffer_uninit(&buffers[i]);
    }
}

int start_playback() {
    ma_result result;
    result = ma_device_start(&pPlaybackDevice);
    if (result != MA_SUCCESS) {
        LOGE("Failed to start playback.");
    }

    return result;
}

void stop_playback() {
    ma_device_stop(&pPlaybackDevice);
}

#endif // PANDALOOP_AUDIOPLAYER_H