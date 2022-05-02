#include "gui.h"
#include <QApplication>

void runGUI(int argc, char **argv)
{

    QApplication app(argc, argv);
    guiMain ui;
    ui.show();
    app.exec();
}

int main(int argc, char **argv)
{
    runGUI(argc, argv);
    return 0;
}
