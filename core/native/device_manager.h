#ifndef PANDALOOP_DEVICEMANAGER_H
#define PANDALOOP_DEVICEMANAGER_H

#include "miniaudio/miniaudio.h"
#include "pandaloop_context.h"

int get_playback_devices_count();

int get_bytes_per_frame(pandaloop_context *context);

#endif // PANDALOOP_DEVICEMANAGER_H