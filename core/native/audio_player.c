#include <string.h>
#include <malloc.h>
#include "miniaudio/miniaudio.h."
#include "logging.h"
#include "constants.h"

#ifndef PANDALOOP_AUDIOPLAYER_H
#define PANDALOOP_AUDIOPLAYER_H

#include "miniaudio/miniaudio.h"

static ma_device device;
static void *playbackBuffer;
ma_uint64 playbackBufferSizeInFrames;
ma_uint64 framesPlayed;

static void playback_data_callback(ma_device *pDevice, void *pOutput, const void *pInput, ma_uint32 frameCount) {
    ma_uint32 framesToPlay;

    if (framesPlayed == playbackBufferSizeInFrames) {
        return;
    }

    if (framesPlayed + frameCount > playbackBufferSizeInFrames) {
        framesToPlay = playbackBufferSizeInFrames - framesPlayed;
    } else {
        framesToPlay = frameCount;
    }

    ma_copy_pcm_frames(pOutput, ma_offset_pcm_frames_ptr(playbackBuffer, framesPlayed, pDevice->playback.format, pDevice->playback.channels), frameCount, pDevice->playback.format, pDevice->playback.channels);
    framesPlayed += framesToPlay;
}

int initializePlaybackDevice(void *buffer, int sizeInFrames) {
    ma_result result;
    ma_device_config deviceConfig;
    playbackBufferSizeInFrames = sizeInFrames;

    ma_uint64 sizeInBytes = sizeInFrames * ma_get_bytes_per_frame(ma_format_f32, CHANNEL_COUNT);
    playbackBuffer = malloc(sizeInBytes);
    memcpy(playbackBuffer, buffer, sizeInBytes);

    deviceConfig = ma_device_config_init(ma_device_type_playback);
    deviceConfig.playback.format = ma_format_f32;
    deviceConfig.playback.channels = CHANNEL_COUNT;
    deviceConfig.noFixedSizedCallback = MA_TRUE;
    deviceConfig.sampleRate = SAMPLE_RATE;
    deviceConfig.dataCallback = playback_data_callback;

    result = ma_device_init(NULL, &deviceConfig, &device);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize playback device.");
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

void uninitalizePlaybackDevice() {
    ma_device_uninit(&device);
}

int startPlayback() {
    ma_result result;

    result = ma_device_start(&device);
    if (result != MA_SUCCESS) {
        LOGD("Failed to start playback.\n");
        uninitalizePlaybackDevice();
        return MA_ERROR;
    }

    LOGD("Playback started. \n");

    return MA_SUCCESS;
}

void stopPlayback() {
    ma_device_stop(&device);
    LOGD("Playback stopped. \n");
}

#endif // PANDALOOP_AUDIOPLAYER_H