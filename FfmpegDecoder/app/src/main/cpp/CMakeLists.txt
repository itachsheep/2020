# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")
set(ANDROID_STL_TYPE "gnustl_static")
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED True)


include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/libavcodec)
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/libvdevice)
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/libvfilter)
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/libvformat)
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/libavutil)
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/libpostproc)
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/libswresample)
include_directories(${CMAKE_SOURCE_DIR}/ffmpeg/include/libwscale)
include_directories(${CMAKE_SOURCE_DIR}/common)
include_directories(${CMAKE_SOURCE_DIR}/jni)
include_directories(${CMAKE_SOURCE_DIR}/decoder)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( fdk-aac
        STATIC
        IMPORTED )

set_target_properties(fdk-aac
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/prebuilt/libfdk-aac.a)


add_library( vo-aacenc
        STATIC
        IMPORTED )

set_target_properties(vo-aacenc
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/prebuilt/libvo-aacenc.a)

add_library( # Sets the name of the library.
        libffmpeg_decoder

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
        ${CMAKE_SOURCE_DIR}/jni/native-lib.cpp
        ${CMAKE_SOURCE_DIR}/decoder/accompany_decoder.cpp
        ${CMAKE_SOURCE_DIR}/decoder/accompany_decoder_controller.cpp
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                    libffmpeg_decoder

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                    fdk-aac
                    ${CMAKE_SOURCE_DIR}/prebuilt/libavfilter.a
                    vo-aacenc
                    ${CMAKE_SOURCE_DIR}/prebuilt/libavformat.a
                    ${CMAKE_SOURCE_DIR}/prebuilt/libavcodec.a
                    ${CMAKE_SOURCE_DIR}/prebuilt/libavutil.a
                    ${CMAKE_SOURCE_DIR}/prebuilt/libswscale.a
                    ${CMAKE_SOURCE_DIR}/prebuilt/libswresample.a
                    ${CMAKE_SOURCE_DIR}/prebuilt/libpostproc.a
                    ${CMAKE_SOURCE_DIR}/prebuilt/libx264.a
        )