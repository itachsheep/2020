#include <jni.h>
#include <string>

#include "../decoder/accompany_decoder_controller.h"
#define LOG_TAG "FFmpegDecoder"

extern "C" JNIEXPORT jstring JNICALL


Java_com_tao_ffmpegdecoder_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


AccompanyDecoderController* decoderController;
jint init(JNIEnv * env, jobject obj, jstring mp3PathParam, jstring pcmPathParam) {
    const char* pcmPath = env->GetStringUTFChars(pcmPathParam, NULL);
    const char* mp3Path = env->GetStringUTFChars(mp3PathParam, NULL);
    decoderController = new AccompanyDecoderController();
    decoderController->Init(mp3Path, pcmPath);
    env->ReleaseStringUTFChars(mp3PathParam, mp3Path);
    env->ReleaseStringUTFChars(pcmPathParam, pcmPath);
    return 1;
}

void decode(JNIEnv * env, jobject obj) {
    LOGI("enter Java_com_phuket_tour_decoder_Mp3Decoder_decode...");
    if(decoderController) {
        decoderController->Decode();
    }
    LOGI("leave Java_com_phuket_tour_decoder_Mp3Decoder_decode...");
}

void destroy(JNIEnv * env, jobject obj) {
    if(decoderController) {
        decoderController->Destroy();
        delete decoderController;
        decoderController = NULL;
    }
}

jint RegisterNatives(JNIEnv *env) {
    jclass clazz = env->FindClass("com/tao/ffmpegdecoder/MainActivity");
    if (clazz == NULL) {
        LOGI("con't find class: com/tao/ffmpegdecoder/MainActivity");
        return JNI_ERR;
    }
    JNINativeMethod methods_MainActivity[] = {
//            {"stringFromJNI", "()Ljava/lang/String;", (void *) stringFromJNI},
//            {"add",           "(II)I",                (void *) add },
            {"init","(Ljava/lang/String;Ljava/lang/String;)I",(void *)init },
            {"decode","()V",(void *)decode },
            {"destroy","()V",(void *)destroy }

    };
    return env->RegisterNatives(clazz, methods_MainActivity,
                                sizeof(methods_MainActivity) / sizeof(methods_MainActivity[0]));
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    jint result = RegisterNatives(env);
    LOGI("RegisterNatives result: %d", result);
    return JNI_VERSION_1_6;
}

