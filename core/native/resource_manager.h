#ifndef PANDALOOP_RESOURCE_MANAGER_H
#define PANDALOOP_RESOURCE_MANAGER_H

#include <stdlib.h>
#include "miniaudio/miniaudio.h"
#include "logging.h"
#include <string.h>
#include "pandaloop_context.h"

int save_audio_file(const char *pFilePath, void *pBuffer, int bufferSize, pandaloop_context *context);

void *load_audio_file(const char *pFilePath, long long int *pBufferSize, pandaloop_context *context);

#endif // PANDALOOP_RESOURCE_MANAGER_H
