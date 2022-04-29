#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;

Colectie::Colectie() : prim(NIL), primLiber(0), dimensiune(0), capacitate(INIT_SIZE)
{
	elems = new TElem[INIT_SIZE];
	frecventa = new int[INIT_SIZE];
	urm = new int[INIT_SIZE];
	prev = new int[INIT_SIZE];
	for (int i = 0; i < capacitate; i++)
	{
		elems[i] = NIL;
		frecventa[i] = 0;
		urm[i] = i + 1;
		prev[i] = i - 1;
	}
	prim = NIL;
	primLiber = 0;
	capacitate = INIT_SIZE;
	urm[capacitate - 1] = NIL;
	prev[0] = NIL;
}
void Colectie::resize()
{
	// resize pentru toti 4 vectorii si initializari
	TElem *newElems = new TElem[capacitate * coef_crestere];
	int *newFrecventa = new int[capacitate * coef_crestere];
	int *newUrm = new int[capacitate * coef_crestere];
	int *newPrev = new int[capacitate * coef_crestere];

	for (int i = 0; i < capacitate * coef_crestere; i++)
	{
		newElems[i] = NIL;
		newFrecventa[i] = 0;
		newUrm[i] = i + 1;
		newPrev[i] = i - 1;
	}
	newUrm[capacitate * coef_crestere - 1] = NIL;
	newPrev[capacitate] = NIL;
	primLiber = capacitate;
	// copiere stage
	for (int i = 0; i < capacitate; i++)
	{
		newElems[i] = elems[i];
		newFrecventa[i] = frecventa[i];
		newUrm[i] = urm[i];
		newPrev[i] = prev[i];
	}

	// delete original stage
	delete[] elems;
	delete[] frecventa;
	delete[] urm;
	delete[] prev;

	// mutare stage
	elems = newElems;
	frecventa = newFrecventa;
	urm = newUrm;
	prev = newPrev;

	// new capacity
	capacitate = capacitate * coef_crestere;
}
void Colectie::adaugaAparitiiMultiple(int nr, TElem elem)
{
	/**
	 * daca nr < 0:
	 * 	arunca exceptie
	 * creeaza iterator
	 * reseteaza iterator
	 * cat timp ( iterator valid si elem nu este gasit)
	 * 	iterator <- urmatorul element
	 * daca iterator valid //insemnand ca elementul cautat exista
	 * 	iterator.frecventa <- iterator.frecventa + nr
	 * 	dimensiune <- dimensiune + nr
	 * 	iesire functie
	 * daca primLiber == NIL //insemnand ca nu mai este spatiu liber
	 * 	apeleaza resize():
	 * 		creeaza 4 noi vectori care corespund componentelor
	 * 		aloca spatiu capacitate * coef_crestere la fiecare dintre vectori
	 * 		initializeaza valorile din cei 4 noi vectori :
	 * 			newelems[i] <- NIL
	 * 			newfrecventa[i] <- 0
	 * 			newurm[i] <- i+1
	 * 			newprev[i] <- i-1
	 * 		newurm[capacitate * coef_crestere -1] = NIL
	 * 		newprev[capacitate] = NIL
	 * 		primLiber <- capacitate
	 * 		pentru fiecare elem din vechii vectori (0...capacitate-1): //proces de copiere valori vechi
	 * 			elems[i] = newelems[i]
	 * 			frecventa[i] = newfrecventa[i]
	 * 			urm[i] = newurm[i]
	 * 			prev[i] = newprev[i]
	 * 		stergere vectori vechi:
	 * 			delete[] elems
	 * 			delete[] frecventa
	 * 			delete[] urm
	 * 			delete[] prev
	 * 		mutare catre noii vectori:
	 * 			elems <- newelems
	 * 			frecventa <- newfrecventa
	 * 			urm <- newurm
	 * 			prev <- newprev
	 * 		capacitate <- capacitate * coef_crestere
	 */
	if (nr < 0)
	{
		throw std::invalid_argument("Introduceti o valoare pozitiva");
	}
	IteratorColectie i = iterator();
	i.prim();
	while (i.valid() && i.element() != elem)
	{
		i.urmator();
	}
	if (i.valid())
	{
		// elementul exista, doar crestem frecventa cu 1
		i.incFrecventa(nr);
		dimensiune += nr;
		return;
	}
	// trebuie sa adaugam un nou element, deci daca nu mai avem loc cream loc
	if (primLiber == NIL)
	{
		resize();
	}

	int copieprimLiber = primLiber;
	primLiber = urm[copieprimLiber];
	urm[copieprimLiber] = prim;
	prev[copieprimLiber] = NIL;
	// leg casuta de prim de cea nou introdusa
	if (prim != NIL)
	{
		prev[prim] = copieprimLiber;
	}
	// mut prim si scriu in noua casuta informatia utila
	prim = copieprimLiber;

	elems[prim] = elem;
	frecventa[prim] = nr;

	dimensiune += nr;
}
void Colectie::adauga(TElem elem)
{

	IteratorColectie i = iterator();
	i.prim();
	while (i.valid() && i.element() != elem)
	{
		i.urmator();
	}
	if (i.valid())
	{
		// elementul exista, doar crestem frecventa cu 1
		i.incFrecventa(1);
		dimensiune++;
		return;
	}
	// trebuie sa adaugam un nou element, deci daca nu mai avem loc cream loc
	if (primLiber == NIL)
	{
		resize();
	}

	int copieprimLiber = primLiber;
	primLiber = urm[copieprimLiber];
	urm[copieprimLiber] = prim;
	prev[copieprimLiber] = NIL;
	// leg casuta de prim de cea nou introdusa
	if (prim != NIL)
	{
		prev[prim] = copieprimLiber;
	}
	// mut prim si scriu in noua casuta informatia utila
	prim = copieprimLiber;

	elems[prim] = elem;
	frecventa[prim] = 1;

	dimensiune++;
}

bool Colectie::sterge(TElem elem)
{
	IteratorColectie i = iterator();
	i.prim();
	while (i.valid() && i.element() != elem)
	{
		i.urmator();
	}
	if (i.valid())
	{
		// a fost gasit, acum depinde daca este ultimul element ramas sau mai sunt si altele
		i.decFrecventa(1);
		if (i.frecventa() == 0)
		{
			// stergem de tot, adica eliminam legaturile;
			int ind = i.curentIndice;
			// se va sterge elems[ind]
			elems[ind] = NIL;
			frecventa[ind] = 0;

			if (prev[ind] != NIL)
			{
				// e ultimul elem
				urm[prev[ind]] = urm[ind];
			}
			if (urm[ind] != NIL)
			{
				prev[urm[ind]] = prev[ind];
			}
			if (ind == prim)
			{
				prim = urm[prim];
			}
			urm[ind] = primLiber;
			prev[ind] = NIL;
			primLiber = ind;
		}
		dimensiune--;
		return true;
	}
	// nu a fost gasit
	return false;
}

bool Colectie::cauta(TElem elem) const
{
	IteratorColectie i = iterator();
	i.prim();

	while (i.valid() && i.element() != elem)
	{
		i.urmator();
	}
	return i.valid();
}

int Colectie::nrAparitii(TElem elem) const
{
	IteratorColectie i(*this);
	while (i.valid() && i.element() != elem)
	{
		i.urmator();
	}
	if (i.valid())
		return i.frecventa();
	return 0;
}

int Colectie::dim() const
{
	/* de adaugat */
	return dimensiune;
}

bool Colectie::vida() const
{
	/* de adaugat */
	return dimensiune == 0;
}

IteratorColectie Colectie::iterator() const
{
	return IteratorColectie(*this);
}

Colectie::~Colectie()
{
	delete[] elems;
	delete[] urm;
	delete[] prev;
	delete[] frecventa;
}
