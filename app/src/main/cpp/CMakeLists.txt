cmake_minimum_required(VERSION 3.4.1)

add_library(anddown SHARED
            anddown/src/anddown.c
            anddown/src/autolink.c
            anddown/src/buffer.c
            anddown/src/document.c
            anddown/src/escape.c
            anddown/src/html.c
            anddown/src/html_blocks.c
            anddown/src/html_smartypants.c
            anddown/src/stack.c
            anddown/src/version.c)

# Include libraries needed for anddown lib
target_link_libraries(anddown
                      android
                      log)


# build native_app_glue as a static lib
add_library(app-glue STATIC
            ${ANDROID_NDK}/sources/android/native_app_glue/android_native_app_glue.c)

# now build app's shared lib
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")

add_library(native-activity SHARED
            activity/src/main.cpp)

target_include_directories(native-activity PRIVATE
                           ${ANDROID_NDK}/sources/android/native_app_glue)

# add lib dependencies
target_link_libraries(native-activity
                      android
                      app-glue
                      EGL
                      GLESv1_CM
                      log)