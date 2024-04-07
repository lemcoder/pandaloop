#ifndef PANDALOOP_AUDIOPLAYER_H
#define PANDALOOP_AUDIOPLAYER_H

#include <string.h>
#include <stdlib.h>
#include "miniaudio/miniaudio.h"
#include "logging.h"
#include "constants.h"

#define TRACKS 4

typedef struct {
    float *data;
    ma_uint64 sizeInFrames;
    ma_uint64 cursor;
} pl_audio_buffer;

int pl_audio_buffer_init(float *data, ma_uint64 sizeInFrames, pl_audio_buffer *buffer);
void pl_audio_buffer_uninit(pl_audio_buffer *buffer);
int initialize_playback_device();
int mix_playback_memory(void *buffer, int sizeInFrames, int trackNumber);
int mix_playback_file(char *path, int trackNumber);
void uninitialize_playback_device();
int start_playback();
void stop_playback();

#endif // PANDALOOP_AUDIOPLAYER_H