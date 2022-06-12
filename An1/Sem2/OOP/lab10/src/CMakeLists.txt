cmake_minimum_required(VERSION 3.5)

project(src LANGUAGES CXX)

set(CMAKE_INCLUDE_CURRENT_DIR ON)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
# QtCreator supports the following variables for Android, which are identical to qmake Android variables.
# Check http://doc.qt.io/qt-5/deployment-android.html for more information.
# They need to be set before the find_package(Qt5 ...) call.

#if(ANDROID)
#    set(ANDROID_PACKAGE_SOURCE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/android")
#    if (ANDROID_ABI STREQUAL "armeabi-v7a")
#        set(ANDROID_EXTRA_LIBS
#            ${CMAKE_CURRENT_SOURCE_DIR}/path/to/libcrypto.so
#            ${CMAKE_CURRENT_SOURCE_DIR}/path/to/libssl.so)
#    endif()
#endif()

find_package(Qt5 COMPONENTS Widgets REQUIRED)

if(ANDROID)
  add_library(src SHARED
    main.cpp
    gui.cpp
    gui.h
    gui.ui
  )
else()
  add_executable(src
    main.cpp
    gui.cpp
    gui.h
    gui.ui
    main.h
    valid/valid.cpp
    valid/valid.h
    service/service.cpp
    service/service.h
    service/undo.h
    repo/repo.cpp
    repo/repo.h
    errors/errors.cpp
    errors/errors.h
    domain/offer.cpp
    domain/offer.h
    domain/listOffers.cpp
    domain/listOffers.h
    domain/cart.cpp
    domain/cart.h
    domain/report.cpp
    domain/report.h
    guiModelView.h
    guiModelView.cpp
  )
endif()

target_link_libraries(src PRIVATE Qt5::Widgets)
