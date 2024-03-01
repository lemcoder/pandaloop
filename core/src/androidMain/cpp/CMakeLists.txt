cmake_minimum_required(VERSION 3.4.1)
project("pandaloop")
set(CMAKE_C_STANDARD 99)

set(sources
        ../../../native/audio_recorder.c
        ../../../native/device_manager.c
)

find_library( log-lib log )
add_library(pl_engine SHARED ${sources})

target_link_libraries(pl_engine ${log-lib})
