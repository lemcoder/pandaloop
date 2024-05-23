%module pandaloop

%include "typemaps.i"
%include "cpointer.i"
%include "arrays_java.i"
%typemap(out) float[]
%{$result = SWIG_JavaArrayOutFloat(jenv, (float *)$1, (long long int)arg1); %}
%apply float[] {float*};

%{
#include "audio_recorder.h"
#include "audio_player.h"
#include "device_manager.h"
#include "resource_manager.h"
%}

%include "audio_recorder.h"
%include "audio_player.h"
%include "device_manager.h"
%include "resource_manager.h"

