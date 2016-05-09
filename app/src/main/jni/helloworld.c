//
// Created by 余鹏 on 15/11/23.
//

#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <ctype.h>
#include <android/log.h>

#define LOG_TAG "helloword"
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

JNIEXPORT jstring JNICALL Java_com_penner_android_NdkActivity_penner(JNIEnv* env, jobject obj, jstring source) {

    size_t i = 1, size;
    const char* data = "<a href=\"https://pubu.zendesk.com/agent/tickets/1141\">#1141 App 工单</a> 已关闭";
    size = strlen(data);

    while (i < size && (isalnum(data[i]) || data[i] == '.' || data[i] == '+' || data[i] == '-')) {
        LOGI("penner--->%zu--->%zu-->%c", i, size, data[i]);
        i++;
    }

    const char *str = (*env)->GetStringUTFChars(env, source, JNI_FALSE);
    LOGI("penner--->%s", str);
    (*env)->ReleaseStringUTFChars(env, source, str);
    return (*env)->NewStringUTF(env, data);
}