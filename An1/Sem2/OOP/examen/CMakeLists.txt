cmake_minimum_required(VERSION 3.5)

project(src LANGUAGES CXX)

set(CMAKE_INCLUDE_CURRENT_DIR ON)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

 



find_package(Qt5 COMPONENTS Widgets REQUIRED)

add_executable(src
    main.cpp
    repo.cpp
    repo.h
    service.cpp
    service.h
    gui.cpp
    gui.h
    valid.h
    valid.cpp
    tests.cpp
    tests.h
    domain.cpp
    domain.h
    )

    target_link_libraries(src PRIVATE Qt5::Widgets)
    