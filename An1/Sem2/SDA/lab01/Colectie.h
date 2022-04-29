#pragma once

#define NULL_TELEM -1
#include "VectorDinamic/VectorDinamic.h"
#include <iostream>
typedef int TElem;

class IteratorColectie;

class Colectie
{
	friend class IteratorColectie;

private:
	/* aici e reprezentarea */
	VectorDinamic D;
	VectorDinamic P;

public:
	// constructorul implicit
	Colectie();

	// adauga un element in colectie
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n - nr elem distincte
	void adauga(TElem e);

	// sterge o aparitie a unui element din colectie
	// returneaza adevarat daca s-a putut sterge
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n - nr elem distincte
	bool sterge(TElem e);

	// verifica daca un element se afla in colectie
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n - nr elem distincte
	bool cauta(TElem elem) const;

	// returneaza numar de aparitii ale unui element in colectie
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n - nr elem distincte
	int nrAparitii(TElem elem) const;

	// intoarce numarul de elemente din colectie;
	// CD: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	int dim() const;

	// verifica daca colectia e vida;
	// CD: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	bool vida() const;

	// returneaza un iterator pe colectie
	// CD: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	IteratorColectie iterator() const;

		void printAll();
	// destructorul colectiei
	~Colectie();
};
