#ifndef PANDALOOP_DEVICEMANAGER_H
#define PANDALOOP_DEVICEMANAGER_H

#include "miniaudio/miniaudio.h"

int get_playback_devices_count();

int get_bytes_per_frame(int channelCount);

#endif // PANDALOOP_DEVICEMANAGER_H