%module pandaloop

%{
#include "audio_recorder.h"
#include "audio_player.h"
#include "device_manager.h"
#include "resource_manager.h"
%}

%include "typemaps.i"
%include "audio_recorder.h"
%include "audio_player.h"
%include "device_manager.h"
%include "resource_manager.h"
