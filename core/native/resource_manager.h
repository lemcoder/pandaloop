#ifndef PANDALOOP_RESOURCE_MANAGER_H
#define PANDALOOP_RESOURCE_MANAGER_H

#include <stdlib.h>
#include "miniaudio/miniaudio.h"
#include "logging.h"
#include <string.h>

int save_audio_file(const char *pFilePath, void *pBuffer, int bufferSize, int channelCount, int sampleRate);

void *load_audio_file(long long int bufferSize, const char *pFilePath);

// TODO add getFileSize method

#endif // PANDALOOP_RESOURCE_MANAGER_H
