#include <iostream>
// #include "tests.h"
#include <QApplication>
#include "gui.h"
int main(int argc, char **argv)
{
    // Tests t;
    // t.runTests();
    QApplication app(argc, argv);
    guiMain UI;
    UI.show();
    app.exec();
    return 0;
}