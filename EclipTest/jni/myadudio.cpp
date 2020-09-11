#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
#include "string.h"
#include "com_example_ecliptest_MyDest.h"
extern "C" JNIEXPORT jstring JNICALL
JNIEXPORT jstring JNICALL Java_com_example_ecliptest_MyDest_stringFromJNI(JNIEnv * env,jobject obj){
    //std::string hello = "Hello from C++";
	char str[] = "欢迎你的到来！";
	char *strTemp = (char *) malloc(strlen(strContent) + strlen(str) + 1);
		//拷贝常量到字符串指针
		strcpy(strTemp,strContent);
		//拼接str1到strTemp
		strcat(strTemp,str);
    return env->env->NewStringUTF(strTemp);
}
