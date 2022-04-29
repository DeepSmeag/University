#include <stdexcept>
#include <iostream>
#pragma once
#include "Colectie.h"
class Colectie;
typedef int TElem;

class IteratorColectie
{
	friend class Colectie;

private:
	// constructorul primeste o referinta catre Container
	// iteratorul va referi primul element din container
	IteratorColectie(const Colectie &c);

	// contine o referinta catre containerul pe care il itereaza
	const Colectie &col;
	/* aici e reprezentarea pecifica a iteratorului*/
	int curentIndice;
	int item;

public:
	// reseteaza pozitia iteratorului la inceputul containerului
	void prim();

	// muta iteratorul in container
	//  arunca exceptie daca iteratorul nu e valid
	void urmator();

	// verifica daca iteratorul e valid (indica un element al containerului)
	// CF: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	bool valid() const;

	// returneaza valoarea elementului din container referit de iterator
	// arunca exceptie daca iteratorul nu e valid
	// CF: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	TElem element() const;

	// CF: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	TElem frecventa() const;

	void incFrecventa(int nr);
	void decFrecventa(int nr);
};
