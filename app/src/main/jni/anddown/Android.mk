LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := penneranddown
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \

LOCAL_SRC_FILES := \
	src/anddown.c \
	src/autolink.c \
	src/buffer.c \
	src/document.c \
	src/escape.c \
	src/html.c \
	src/html_blocks.c \
	src/html_smartypants.c \
	src/stack.c \
	src/version.c \

LOCAL_C_INCLUDES += src

include $(BUILD_SHARED_LIBRARY)
