cmake_minimum_required(VERSION 3.18.1)


project("morii")


add_library(
        morii
        SHARED
        morii.cpp)


find_library(log-lib
        log)


add_library(avcodec
        SHARED
        IMPORTED)
set_target_properties(avcodec
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavcodec.so)

add_library(ffmpeg
        SHARED

        ${CMAKE_SOURCE_DIR}/fftools/ffmpeg.c
        ${CMAKE_SOURCE_DIR}/fftools/ffmpeg_hw.c
        ${CMAKE_SOURCE_DIR}/fftools/ffmpeg_opt.c
        ${CMAKE_SOURCE_DIR}/fftools/ffmpeg_filter.c
        ${CMAKE_SOURCE_DIR}/fftools/cmdutils.c
        )

add_library(postproc
        SHARED
        IMPORTED)
set_target_properties(postproc
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libpostproc.so)

add_library(avdevice
        SHARED
        IMPORTED)
set_target_properties(avdevice
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavdevice.so)

add_library(avfilter
        SHARED
        IMPORTED)
set_target_properties(avfilter
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavfilter.so)

add_library(avformat
        SHARED
        IMPORTED)
set_target_properties(avformat
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavformat.so)

add_library(avutil
        SHARED
        IMPORTED)
set_target_properties(avutil
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libavutil.so)

add_library(swresample
        SHARED
        IMPORTED)
set_target_properties(swresample
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libswresample.so)

add_library(swscale
        SHARED
        IMPORTED)
set_target_properties(swscale
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/lib/${CMAKE_ANDROID_ARCH_ABI}/libswscale.so)


include_directories(${CMAKE_SOURCE_DIR}/include
        )

target_link_libraries(
        morii

        ffmpeg

        ${log-lib})
target_link_libraries(
        ffmpeg
        avfilter
        avformat
        postproc
        avdevice
        avcodec
        avutil
        swresample
        swscale
        ${log-lib})