#include <jni.h>
#include <string>
#include <android/log.h>
#include <filesystem>
#include "mp3_encoder.h"
#include "../thirdparty/include/lame/lame.h"
#include "../common/CommonTools.h"

extern "C" JNIEXPORT jstring JNICALL


#define __FILENAME__ (strrchr(__FILE__, '/') + 1) // 默认使用这种方式


#define TAG "Mp3Encoder"
#define LOG_TAG "Mp3Encoder"

#define LOGD(format, ...) __android_log_print(ANDROID_LOG_DEBUG, TAG,\
        "[%s][%s][%d]: " format, __FILENAME__, __FUNCTION__, __LINE__, ##__VA_ARGS__);


Java_com_tao_ndktest_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";

    LOGD("stringFromJNI ---->");
    return env->NewStringUTF(hello.c_str());
}


jint add(JNIEnv *env, jclass clazz, jint a, jint b) {
    LOGD("add ---> ")
    return a + b;
}



Mp3Encoder::Mp3Encoder() {
}

Mp3Encoder::~Mp3Encoder() {
}

int Mp3Encoder::Init(const char* pcmFilePath, const char *mp3FilePath, int sampleRate, int channels, int bitRate) {
    int ret = -1;
    pcmFile = fopen(pcmFilePath, "rb");
    if(pcmFile) {
        mp3File = fopen(mp3FilePath, "wb");
        if(mp3File) {
            lameClient = lame_init();
            lame_set_in_samplerate(lameClient, sampleRate);
            lame_set_out_samplerate(lameClient, sampleRate);
            lame_set_num_channels(lameClient, channels);
            lame_set_brate(lameClient, bitRate / 1000);
            lame_init_params(lameClient);
            ret = 0;
        }
    }
    return ret;
}

void Mp3Encoder::Encode() {
    int bufferSize = 1024 * 256;
    short* buffer = new short[bufferSize / 2];
    short* leftBuffer = new short[bufferSize / 4];
    short* rightBuffer = new short[bufferSize / 4];
    uint8_t* mp3_buffer = new uint8_t[bufferSize];
    int readBufferSize = 0;
    while ((readBufferSize = fread(buffer, 2, bufferSize / 2, pcmFile)) > 0) {
        for (int i = 0; i < readBufferSize; i++) {
            if (i % 2 == 0) {
                leftBuffer[i / 2] = buffer[i];
            } else {
                rightBuffer[i / 2] = buffer[i];
            }
        }
        int wroteSize = lame_encode_buffer(lameClient, (short int *) leftBuffer, (short int *) rightBuffer, readBufferSize / 2, mp3_buffer, bufferSize);
        fwrite(mp3_buffer, 1, wroteSize, mp3File);
    }
    delete[] buffer;
    delete[] leftBuffer;
    delete[] rightBuffer;
    delete[] mp3_buffer;
}

void Mp3Encoder::Destory() {
    if(pcmFile) {
        fclose(pcmFile);
    }
    if(mp3File) {
        fclose(mp3File);
        lame_close(lameClient);
    }
}

Mp3Encoder* encoder = NULL;

jint init(JNIEnv * env, jobject obj, jstring pcmPathParam, jint channels, jint bitRate,
         jint sampleRate, jstring mp3PathParam) {
    const char* pcmPath = env->GetStringUTFChars(pcmPathParam, NULL);
    const char* mp3Path = env->GetStringUTFChars(mp3PathParam, NULL);
    LOGI("mp3Path is %s...", mp3Path);
    encoder = new Mp3Encoder();
    encoder->Init(pcmPath, mp3Path, sampleRate, channels, bitRate);
    env->ReleaseStringUTFChars(mp3PathParam, mp3Path);
    env->ReleaseStringUTFChars(pcmPathParam, pcmPath);
    return 0;
}

void encode(JNIEnv * env, jobject obj) {
    if(NULL != encoder) {
        encoder->Encode();
    }
}

void destroy(JNIEnv * env, jobject obj) {
    if(NULL != encoder) {
        encoder->Destory();
        delete encoder;
        encoder = NULL;
    }
}

jint RegisterNatives(JNIEnv *env) {
    jclass clazz = env->FindClass("com/tao/ndktest/MainActivity");
    if (clazz == NULL) {
        LOGD("con't find class: com/tao/ndktest/MainActivity");
        return JNI_ERR;
    }
    JNINativeMethod methods_MainActivity[] = {
//            {"stringFromJNI", "()Ljava/lang/String;", (void *) stringFromJNI},
            {"add",           "(II)I",                (void *) add },
            {"init","(Ljava/lang/String;IIILjava/lang/String;)I",(void *)init },
            {"encode","()V",(void *)encode },
            {"destroy","()V",(void *)destroy }

    };
    // int len = sizeof(methods_MainActivity) / sizeof(methods_MainActivity[0]);
    return env->RegisterNatives(clazz, methods_MainActivity,
                                sizeof(methods_MainActivity) / sizeof(methods_MainActivity[0]));
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    jint result = RegisterNatives(env);
    LOGD("RegisterNatives result: %d", result);
    return JNI_VERSION_1_6;
}



