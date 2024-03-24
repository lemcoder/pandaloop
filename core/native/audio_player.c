#include <string.h>
#include <malloc.h>
#include "miniaudio/miniaudio.h."
#include "logging.h"
#include "constants.h"

#ifndef PANDALOOP_AUDIOPLAYER_H
#define PANDALOOP_AUDIOPLAYER_H

#include "miniaudio/miniaudio.h"

// Memory playback variables
static ma_device pPlaybackDevice;
static void *pPlaybackBuffer = NULL;
static ma_uint64 playbackBufferSizeInFrames = 0;
static ma_uint64 framesPlayed = 0;

// File playback variables
static ma_sound sound;
static ma_decoder decoder;
static ma_engine engine;

typedef enum {
    file,
    memory
} playbackMode;

static playbackMode currentMode;

static void playback_data_callback(ma_device *pDevice, void *pOutput, const void *pInput, ma_uint32 frameCount) {
    ma_uint32 framesToPlay;

    if (framesPlayed == playbackBufferSizeInFrames) {
        framesPlayed = 0; // default looping
    }

    if (framesPlayed + frameCount > playbackBufferSizeInFrames) {
        framesToPlay = playbackBufferSizeInFrames - framesPlayed;
    } else {
        framesToPlay = frameCount;
    }

    ma_copy_pcm_frames(pOutput, ma_offset_pcm_frames_ptr(pPlaybackBuffer, framesPlayed, pDevice->playback.format, pDevice->playback.channels), frameCount, pDevice->playback.format, pDevice->playback.channels);
    framesPlayed += framesToPlay;
}

int initialize_playback_memory(void *buffer, int sizeInFrames) {
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
        return MA_ERROR;
    }

    playbackBufferSizeInFrames = sizeInFrames;
    // Copy memory to prevent it from being garbage collected when calling from JVM
    ma_uint64 sizeInBytes = sizeInFrames * ma_get_bytes_per_frame(ma_format_f32, CHANNEL_COUNT);
    pPlaybackBuffer = malloc(sizeInBytes);
    memcpy(pPlaybackBuffer, buffer, sizeInBytes);

    currentMode = memory;
    return MA_SUCCESS;
}

int initialize_playback_file(char *path) {
    ma_result result;

    result = ma_engine_init(NULL, &engine);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize engine: %d", result);
        return MA_ERROR;
    }

    result = ma_decoder_init_file(path, NULL, &decoder);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize decoder: %d", result);
        return MA_ERROR;
    }

    result = ma_sound_init_from_data_source(&engine, &decoder, 0, NULL, &sound);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize sound.\n");
        return MA_ERROR;
    }

    return MA_SUCCESS;
}

void uninitalize_playback() {
    switch (currentMode) {
        case memory:
            ma_device_uninit(&pPlaybackDevice);
            framesPlayed = 0;
            playbackBufferSizeInFrames = 0;
            free(pPlaybackBuffer);
            break;
        case file:
            ma_decoder_uninit(&decoder);
            ma_sound_uninit(&sound);
            break;
    }
}

int start_playback() {
    ma_result result;

    switch (currentMode) {
        case memory:
            framesPlayed = 0;
            result = ma_device_start(&pPlaybackDevice);
            if (result != MA_SUCCESS) {
                LOGE("Failed to start playback.");
                uninitalize_playback();
                return MA_ERROR;
            }
            break;
        case file:
            ma_sound_seek_to_pcm_frame(&sound, 0);
            ma_sound_set_looping(&sound, MA_TRUE);
            result = ma_sound_start(&sound);
            if (result != MA_SUCCESS) {
                LOGE("Failed to start playback.");
                uninitalize_playback();
                return MA_ERROR;
            }
            break;
    }


    LOGD("Playback started. \n");

    return MA_SUCCESS;
}

void stop_playback() {
    switch (currentMode) {
        case memory:
            ma_device_stop(&pPlaybackDevice);
            break;
        case file:
            ma_sound_stop(&sound);
            break;
    }
    LOGD("Playback stopped. \n");
}

#endif // PANDALOOP_AUDIOPLAYER_H