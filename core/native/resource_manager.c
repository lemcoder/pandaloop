#include <stdlib.h>
#include "miniaudio/miniaudio.h"
#include "logging.h"
#include <string.h>
#include "constants.h"

#ifndef PANDALOOP_RESOURCE_MANAGER_C
#define PANDALOOP_RESOURCE_MANAGER_C

float *loadAudioFile(const char *path) {
    ma_result result;
    ma_decoder decoder;

    result = ma_decoder_init_file(path, NULL, &decoder);
    if (result != MA_SUCCESS) {
        LOGE("Failed to load audio file\n");
        return NULL;
    }

    ma_uint64 frameCount;
    ma_decoder_get_length_in_pcm_frames(&decoder, &frameCount);
    float *buffer = (float *) malloc(frameCount * sizeof(float) * decoder.outputChannels);
    if (buffer == NULL) {
        LOGE("Failed to allocate memory for buffer\n");
        ma_decoder_uninit(&decoder);
        return NULL;
    }

    ma_uint64 framesRead;
    ma_decoder_read_pcm_frames(&decoder, buffer, frameCount, &framesRead);
    if (framesRead != frameCount) {
        LOGE("Failed to read all PCM frames\n");
        free(buffer);
        ma_decoder_uninit(&decoder);
        return NULL;
    }

    return buffer;
}

int saveAudioFile(const char *path, float *buffer, int bufferSize) {
    ma_encoder_config config = ma_encoder_config_init(ma_encoding_format_wav, ma_format_f32, CHANNEL_COUNT, SAMPLE_RATE);

    ma_encoder encoder;
    if (ma_encoder_init_file(path, &config, &encoder) != MA_SUCCESS) {
        LOGE("Failed to initialize output file");
        return MA_ERROR;
    }

    ma_uint64 framesWritten = 0;
    if (ma_encoder_write_pcm_frames(&encoder, buffer, bufferSize, &framesWritten) != MA_SUCCESS) {
        ma_encoder_uninit(&encoder);
        LOGE("Failed to write audio data");
        return MA_ERROR;
    }

    LOGE("Frames written to file: %llu", framesWritten);

    ma_encoder_uninit(&encoder);

    return MA_SUCCESS;
}

int getBytesPerFrame() {
    return ma_get_bytes_per_frame(ma_format_f32, CHANNEL_COUNT);
}

#endif //PANDALOOP_RESOURCE_MANAGER_C
