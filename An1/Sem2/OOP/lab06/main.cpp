#include "main.h"

int main(int argc, char **argv)
{
    UI ui;
    Test tests;
    tests.runTests();
    ui.runUI();

    return 0;
}
