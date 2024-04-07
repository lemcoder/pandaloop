#ifndef PANDALOOP_LOGGING_H
#define PANDALOOP_LOGGING_H

#ifdef __ANDROID__

#include <android/log.h>

#define TAG "PL_ENGINE"

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,    TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,     TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,     TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,    TAG, __VA_ARGS__)

#else // iOS or other platforms

#include <stdio.h>

#define LOGE(...) printf("Error: " __VA_ARGS__)
#define LOGW(...) printf("Warning: " __VA_ARGS__)
#define LOGI(...) printf("Info: " __VA_ARGS__)
#define LOGD(...) printf("Debug: " __VA_ARGS__)

#endif // __ANDROID__

#endif // PANDALOOP_LOGGING_H
