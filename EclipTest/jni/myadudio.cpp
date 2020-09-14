#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
#include "string.h"
#include "com_example_ecliptest_MyDest.h"

//extern "C" JNIEXPORT jstring JNICALL

#include "accompany_decoder_controller.h"

#define LOG_TAG "Mp3Decoder"

AccompanyDecoderController* decoderController;


#ifdef __cplusplus
extern "C"
{
#endif

JNIEXPORT jstring JNICALL Java_com_example_ecliptest_MyDest_stringFromJNI
  (JNIEnv * env, jobject obj){
    //std::string hello = "Hello from C++";
	char str[] = "hello this is test jni";
    return env->NewStringUTF(str);
}


/*
 * Class:     com_example_ecliptest_MyDest
 * Method:    init
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_example_ecliptest_MyDest_init
(JNIEnv * env, jobject obj, jstring mp3PathParam, jstring pcmPathParam) {
	const char* pcmPath = env->GetStringUTFChars(pcmPathParam, NULL);
	const char* mp3Path = env->GetStringUTFChars(mp3PathParam, NULL);
	decoderController = new AccompanyDecoderController();
	decoderController->Init(mp3Path, pcmPath);
	env->ReleaseStringUTFChars(mp3PathParam, mp3Path);
	env->ReleaseStringUTFChars(pcmPathParam, pcmPath);
}

/*
 * Class:     com_example_ecliptest_MyDest
 * Method:    decode
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_ecliptest_MyDest_decode
(JNIEnv * env, jobject obj) {
	LOGI("enter Java_com_phuket_tour_decoder_Mp3Decoder_decode...");
	if(decoderController) {
		decoderController->Decode();
	}
	LOGI("leave Java_com_phuket_tour_decoder_Mp3Decoder_decode...");
}

/*
 * Class:     com_example_ecliptest_MyDest
 * Method:    destroy
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_ecliptest_MyDest_destroy
(JNIEnv * env, jobject obj) {
	if(decoderController) {
		decoderController->Destroy();
		delete decoderController;
		decoderController = NULL;
	}
}



#ifdef __cplusplus
}
#endif
