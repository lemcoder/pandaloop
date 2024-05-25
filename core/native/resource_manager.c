#include "resource_manager.h"

#ifndef PANDALOOP_RESOURCE_MANAGER_C
#define PANDALOOP_RESOURCE_MANAGER_C

int save_audio_file(const char *pFilePath, void *pBuffer, int bufferSize, int channelCount, int sampleRate) {
    ma_encoder_config config = ma_encoder_config_init(ma_encoding_format_wav, ma_format_f32, channelCount, sampleRate);

    ma_encoder encoder;
    if (ma_encoder_init_file(pFilePath, &config, &encoder) != MA_SUCCESS) {
        LOGE("Failed to initialize output file");
        return MA_ERROR;
    }

    ma_uint64 framesWritten = 0;
    if (ma_encoder_write_pcm_frames(&encoder, pBuffer, bufferSize, &framesWritten) != MA_SUCCESS) {
        ma_encoder_uninit(&encoder);
        LOGE("Failed to write audio data");
        return MA_ERROR;
    }

    LOGE("Frames written to file: %llu", framesWritten);

    ma_encoder_uninit(&encoder);

    return MA_SUCCESS;
}

void *load_audio_file(long long int bufferSize, const char *pFilePath) {
    ma_result result;
    ma_decoder decoder;
    ma_uint64 framesAvailable = 0;
    void *tempBuffer = NULL;

    result = ma_decoder_init_file(pFilePath, NULL, &decoder);
    if (result != MA_SUCCESS) {
        LOGE("Failed to initialize decoder: %d", result);
        return NULL;
    }

    ma_decoder_get_available_frames(&decoder, &framesAvailable);
    tempBuffer = calloc(bufferSize, 1);
    if (tempBuffer == NULL) {
        LOGD("Failed to initialize buffer. Out of memory");
        return NULL;
    }

    ma_decoder_read_pcm_frames(&decoder, tempBuffer, framesAvailable, NULL);

    return tempBuffer;
}

#endif //PANDALOOP_RESOURCE_MANAGER_C
