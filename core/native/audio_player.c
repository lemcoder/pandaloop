#include <string.h>
#include <malloc.h>
#include "miniaudio/miniaudio.h."
#include "logging.h"
#include "constants.h"

#ifndef PANDALOOP_AUDIOPLAYER_H
#define PANDALOOP_AUDIOPLAYER_H

#include "miniaudio/miniaudio.h"

static ma_sound sound;
static ma_decoder decoder;
static ma_engine engine;
static void* playbackBuffer;
static ma_decoder_config config;

int initializePlaybackDevice(void *buffer, size_t bufferSize) {
    ma_result result;

    playbackBuffer = malloc(bufferSize);
    memcpy(playbackBuffer, buffer, bufferSize);

    config.encodingFormat = ma_encoding_format_wav;
    config.format = ma_format_f32;
    config.channels = CHANNEL_COUNT;
    config.sampleRate = SAMPLE_RATE;

    result = ma_engine_init(NULL, &engine);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize engine: %d", result);
        return MA_ERROR;
    }

    result = ma_decoder_init_file("/data/user/0/pl.lemanski.pandaloop.test/cache/test2.wav", NULL, &decoder);
    // result = ma_decoder_init_memory(playbackBuffer, bufferSize, &config, &decoder);
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

void uninitalizePlaybackDevice() {
    ma_decoder_uninit(&decoder);
    ma_sound_uninit(&sound);
}

int startPlayback() {
    ma_result result;
    ma_sound_seek_to_pcm_frame(&sound, 0);

    result = ma_sound_start(&sound);
    if (result != MA_SUCCESS) {
        LOGD("Failed to start playback.\n");
        uninitalizePlaybackDevice();
        return MA_ERROR;
    }

    LOGD("Playback started. \n");

    return MA_SUCCESS;
}

void stopPlayback() {
    ma_sound_stop(&sound);
    LOGD("Playback stopped. \n");
}

#endif // PANDALOOP_AUDIOPLAYER_H