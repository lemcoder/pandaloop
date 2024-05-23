cmake_minimum_required(VERSION 3.4.1)
project("pandaloop")
set(CMAKE_C_STANDARD 99)

# Set policies to avoid warnings
if (POLICY CMP0078)
    cmake_policy(SET CMP0078 NEW) # SWIG generates standard target names
endif ()
if (POLICY CMP0086)
    cmake_policy(SET CMP0086 NEW) # SWIG honors SWIG_MODULE_NAME via -module flag
endif ()

find_package(SWIG REQUIRED)
include(UseSWIG)

include_directories(${CMAKE_CURRENT_SOURCE_DIR})
include_directories(${JAVA_INCLUDE_PATH})
include_directories(${JNI_INCLUDE_DIRS})
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/../../../native)

set(SWIG_INTERFACE pandaloop.i)
set(CMAKE_SWIG_FLAGS -package pl.lemanski.pandaloop.core.engine.jni)

set_property(SOURCE pandaloop.i PROPERTY SWIG_MODULE_NAME Pandaloop)
set(PANDALOOP_SOURCES
        ../../../native/audio_recorder.h
        ../../../native/audio_recorder.c
        ../../../native/audio_player.h
        ../../../native/audio_player.c
        ../../../native/device_manager.h
        ../../../native/device_manager.c
        ../../../native/resource_manager.h
        ../../../native/resource_manager.c
)

add_library(
        pl_engine
        SHARED
        ${PANDALOOP_SOURCES}
)
swig_add_library(
        pl_engine_swig
        LANGUAGE java
        SOURCES ${SWIG_INTERFACE}
        OUTFILE_DIR ${CMAKE_SOURCE_DIR}/swig
        OUTPUT_DIR ${CMAKE_SOURCE_DIR}/swig
)


target_link_libraries(pl_engine_swig ${JNI_LIBRARIES} pl_engine)
target_link_libraries(pl_engine log OpenSLES ${JNI_LIBRARIES})