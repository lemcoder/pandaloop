#include "resource_manager.h"

#ifndef PANDALOOP_RESOURCE_MANAGER_C
#define PANDALOOP_RESOURCE_MANAGER_C

int save_audio_file(const char *path, void *buffer, int bufferSize) {
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

int get_bytes_per_frame() {
    return ma_get_bytes_per_frame(ma_format_f32, CHANNEL_COUNT);
}

#endif //PANDALOOP_RESOURCE_MANAGER_C
