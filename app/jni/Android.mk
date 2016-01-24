LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := invocation

LOCAL_SRC_FILES += invocation.cpp

include $(BUILD_SHARED_LIBRARY)
