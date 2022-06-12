#include <iostream>
// #include <QApplication>
// #include "gui.h"
#include "tests.h"
using namespace std;

int main(int argc, char **argv)
{
    // QApplication app(argc, argv);
    // GUI gui;
    // gui.show();
    // app.exec();
    Tests tests;
    tests.run();
    return 0;
}