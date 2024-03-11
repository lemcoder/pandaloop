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

int saveAudioFile(const char *path, float *buffer) {
    FILE *file = fopen(path, "wb");
    if (file == NULL) {
        printf("Failed to open file for writing: %s\n", path);
        return MA_ERROR;
    }

    int frameCount = sizeof(float) * CHANNEL_COUNT;
    size_t bytesWritten;
    fwrite(buffer, frameCount, bytesWritten, file);
    if (bytesWritten != frameCount) {
        LOGE("Failed to write all audio data to file\n");
        fclose(file);
        return MA_ERROR;
    }

    fclose(file);
    return MA_SUCCESS;
}

#endif //PANDALOOP_RESOURCE_MANAGER_C
