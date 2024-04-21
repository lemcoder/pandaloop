#ifndef PANDALOOP_RESOURCE_MANAGER_H
#define PANDALOOP_RESOURCE_MANAGER_H

#include <stdlib.h>
#include "miniaudio/miniaudio.h"
#include "logging.h"
#include <string.h>
#include "constants.h"

int save_audio_file(const char *pFilePath, void *pBuffer, int bufferSize);

int get_bytes_per_frame();

void *load_audio_file(const char *pFilePath, ma_uint64 *pBufferSize);

#endif // PANDALOOP_RESOURCE_MANAGER_H
