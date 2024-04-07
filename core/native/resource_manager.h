#ifndef PANDALOOP_RESOURCE_MANAGER_H
#define PANDALOOP_RESOURCE_MANAGER_H

#include <stdlib.h>
#include "miniaudio/miniaudio.h"
#include "logging.h"
#include <string.h>
#include "constants.h"

int save_audio_file(const char *path, void *buffer, int bufferSize);
int get_bytes_per_frame();

#endif // PANDALOOP_RESOURCE_MANAGER_H
