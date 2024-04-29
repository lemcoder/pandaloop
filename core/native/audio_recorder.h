#ifndef PANDALOOP_AUDIORECORDER_H
#define PANDALOOP_AUDIORECORDER_H

#include "miniaudio/miniaudio.h"
#include <stdlib.h>
#include <string.h>
#include "logging.h"
#include "pandaloop_context.h"
#include <unistd.h>

int initialize_recording(int sizeInFrames, pandaloop_context* context);
void uninitialize_recording();
void *stop_recording();
int start_recording();

#endif // PANDALOOP_AUDIORECORDER_H
