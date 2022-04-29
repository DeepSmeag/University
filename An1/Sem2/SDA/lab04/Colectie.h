#pragma once
#include "cassert"
#define NULL_TELEM -1
#define NIL -1
#define INIT_SIZE 20
#define coef_crestere 4
typedef int TElem;
// const int INIT_SIZE = 2;
// const int coef_crestere = 3;
class IteratorColectie;

// se defineste tipul PNod ca fiind adresa unui Nod

class Colectie
{
	friend class IteratorColectie;

private:
	/* aici e reprezentarea */
	TElem *elems;
	int *frecventa;
	int *urm;
	int *prev;
	int prim;
	int primLiber;
	int dimensiune;
	int capacitate;

public:
	// constructorul implicit
	Colectie();

	// adauga un element in colectie
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n = dimensiune
	void adaugaAparitiiMultiple(int nr, TElem elem);
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n = dimensiune
	void adauga(TElem e);
	void resize();
	// sterge o aparitie a unui element din colectie
	// returneaza adevarat daca s-a putut sterge
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n = dimensiune
	bool sterge(TElem e);

	// elimina nr aparitii ale acestui elem; in cazul in care elem apare de mai putin de nr ori, aparitiile sale vor fi eliminate
	// returneaza nr de aparitii eliminate ale elem
	// arunca exceptie in cazul in care nr este negativ
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n - nr elem distincte
	// int eliminaAparitii(int nr, TElem elem);

	// verifica daca un element se afla in colectie
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n = dimensiune
	bool cauta(TElem elem) const;

	// returneaza numar de aparitii ale unui element in colectie
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n = dimensiune
	int nrAparitii(TElem elem) const;

	// intoarce numarul de elemente din colectie;
	// CF: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	int dim() const;

	// verifica daca colectia e vida;
	// CF: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	bool vida() const;

	// returneaza un iterator pe colectie
	// CF: theta(1); CD: theta(1); CM: theta(1); CT: theta(1)
	IteratorColectie iterator() const;

	// destructorul colectiei
	~Colectie();
};
