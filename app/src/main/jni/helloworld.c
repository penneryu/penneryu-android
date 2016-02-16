//
// Created by 余鹏 on 15/11/23.
//

#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <android/log.h>

#define LOG_TAG "helloword"
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

JNIEXPORT jstring JNICALL Java_com_penner_android_NdkActivity_penner(JNIEnv* env, jobject obj, jstring source) {
    const char *str = (const char *)(*env)->GetStringUTFChars( env,source, JNI_FALSE );
    LOGI("dufresne--->%s",(const char *)str);
    (*env)->ReleaseStringUTFChars(env, source, (const char *)str);
    return (*env)->NewStringUTF(env, "Hello World! I am Native interface");
}