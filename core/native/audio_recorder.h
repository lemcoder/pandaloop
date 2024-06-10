#ifndef PANDALOOP_AUDIORECORDER_H
#define PANDALOOP_AUDIORECORDER_H

#include "miniaudio/miniaudio.h"
#include <stdlib.h>
#include <string.h>
#include "logging.h"
#include <unistd.h>

int initialize_recording(long long int sizeInBytes, int channelCount, int sampleRate);
void uninitialize_recording();
void* stop_recording(long long int sizeInBytes);
int start_recording();

#endif // PANDALOOP_AUDIORECORDER_H
