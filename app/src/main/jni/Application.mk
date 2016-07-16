APP_STL := c++_static
APP_CPPFLAGS += -fexceptions
LIBCXX_FORCE_REBUILD := true

APP_BUILD_SCRIPT := jni/anddown/Android.mk \
                    jni/pdfium/Android.mk

#For ANativeWindow support
APP_PLATFORM = android-23

APP_ABI :=  armeabi \
            armeabi-v7a \
            arm64-v8a \
            mips \
            x86 \
            x86_64