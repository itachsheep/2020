LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := myadudio
LOCAL_SRC_FILES := myadudio.cpp

include $(BUILD_SHARED_LIBRARY)
