#include "Tests.h"
#include "Repository.h"
#include "Domain.h"
#include "User_interface.h"
#include "Service.h"
#include "Undo.h"
// #include <crtdbg.h>

int main()
{
	run_tests();
	Repository *repo = create();
	undoStruct *undo = undoCreateHistory(repo);
	Service *serv = create_service(repo);
	UI *ui = create_UI(serv, undo);
	UI_RUN(ui);

	return 0;
}