#include <iostream>
#include "moore.h"
using namespace std;

void p2();

void afisp2();

// Sa se determine închiderea transitivă a unui graf orientat.
//  (Închiderea tranzitivă poate fi reprezentată ca matricea
//  care descrie, pentru fiecare vârf în parte, care sunt
//   vârfurile accesibile din acest vârf.
//    Matricea inchiderii tranzitive arată unde se poate ajunge
//     din fiecare vârf.) ex. figura inchidere_tranzitiva.png -
//      pentru graful de sus se construieste matricea de jos
//       care arata inchiderea tranzitiva.