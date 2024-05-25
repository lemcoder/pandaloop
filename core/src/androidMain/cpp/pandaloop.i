%module pandaloop

%include "typemaps.i"
%include "cpointer.i"
%include "arrays_java.i"

// Typemaps to handle void* and byte[] conversions
%typemap(jni) void* "jbyteArray"
%typemap(jtype) void* "byte[]"
%typemap(jstype) void* "byte[]"

// Input typemap: Convert byte[] to void*
%typemap(in) void * {
    $1 = JCALL2(GetByteArrayElements, jenv, $input, NULL);
}

// Argout typemap: Ensure changes in void* are reflected back to byte[]
%typemap(out) void* {
    jsize length = (jsize)arg1;  // assume arg1 holds actual length
    $result = JCALL1(NewByteArray, jenv, length);
    if ($result) {
        JCALL4(SetByteArrayRegion, jenv, $result, 0, length, result);
        free(result);
    }
}

// Prevent default freearg typemap from being used
%typemap(freearg) void* ""


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

