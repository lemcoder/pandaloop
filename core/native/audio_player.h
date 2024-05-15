#ifndef PANDALOOP_AUDIOPLAYER_H
#define PANDALOOP_AUDIOPLAYER_H

#include <string.h>
#include <stdlib.h>
#include "miniaudio/miniaudio.h"
#include "logging.h"
#include "pandaloop_context.h"

int initialize_playback_device(pandaloop_context *context);

int set_playback_buffer(float *buffer, ma_uint64 sizeInBytes);

void uninitialize_playback_device();

int start_playback();

void stop_playback();

#endif // PANDALOOP_AUDIOPLAYER_H