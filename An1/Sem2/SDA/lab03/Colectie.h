#pragma once

#define NULL_TELEM -1
typedef int TElem;

class IteratorColectie;

class Nod;

// se defineste tipul PNod ca fiind adresa unui Nod

typedef Nod *PNod;

class Nod
{

private:
	TElem elem;
	TElem frecventa;
	PNod urm;

public:
	friend class Colectie;
	Nod(TElem elem, TElem frecventa, PNod urm);
	TElem getElement();
	void setElement(TElem newElem);
	TElem getFrecventa();
	void setFrecventa(TElem newFreq);
	PNod urmator();
};

class Colectie
{
	friend class IteratorColectie;

private:
	/* aici e reprezentarea */
	PNod prim;
	int dimensiune;

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

	// elimina nr aparitii ale acestui elem; in cazul in care elem apare de mai putin de nr ori, aparitiile sale vor fi eliminate
	// returneaza nr de aparitii eliminate ale elem
	// arunca exceptie in cazul in care nr este negativ
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n - nr elem distincte
	int eliminaAparitii(int nr, TElem elem);

	// verifica daca un element se afla in colectie
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n - nr elem distincte
	bool cauta(TElem elem) const;

	// returneaza numar de aparitii ale unui element in colectie
	// CF: theta(1); CD: theta(n); CM: O(n); CT: O(n) ; n - nr elem distincte
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
