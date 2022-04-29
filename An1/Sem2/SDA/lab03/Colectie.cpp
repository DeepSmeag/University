#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;

Nod::Nod(TElem elem, TElem frecventa, PNod urm)
{
	this->elem = elem;
	this->frecventa = frecventa;
	this->urm = urm;
}

TElem Nod::getElement()
{
	return elem;
}
void Nod::setElement(TElem newElem)
{
	elem = newElem;
}
TElem Nod::getFrecventa()
{
	return frecventa;
}
void Nod::setFrecventa(TElem newFreq)
{
	frecventa = newFreq;
}

PNod Nod::urmator()
{
	return urm;
}

Colectie::Colectie()
{
	/* de adaugat */
	prim = nullptr;
	dimensiune = 0;
}

void Colectie::adauga(TElem elem)
{
	/* de adaugat */
	if (cauta(elem))
	{
		IteratorColectie i = iterator();
		i.prim();
		while (i.element() != elem)
		{
			i.urmator();
		}
		i.incFrecventa();
	}
	else
	{

		PNod q = new Nod(elem, 1, nullptr);
		q->urm = prim;
		prim = q;
	}
	dimensiune++;
}
int Colectie::eliminaAparitii(int nr, TElem elem)
{
	/**
	 * @brief Pseudocod
	 * frec<-0
	 * daca exista(elem) // cautare
	 * 	creeaza iterator
	 * 	cat timp iterator != elem
	 * 		iterator.urmator()
	 * 	daca iterator.frecventa() <= nr
	 * 		frec<-iterator.frecventa()
	 * 		elimina element cu totul
	 * 	altfel
	 * 		frec<-nr
	 * 		scade nr aparitii din frecventa
	 * 	dim<-dim - frec
	 * returneaza frec
	 *
	 */
	int frecv = 0;
	if (nr < 0)
	{
		throw std::invalid_argument("Nr nu poate fi negativ");
	}
	if (cauta(elem))
	{

		IteratorColectie i = iterator();
		i.prim();
		while (i.element() != elem)
		{
			i.urmator();
		}
		if (i.frecventa() <= nr)
		{
			frecv = i.frecventa();
			PNod p = prim;
			if (p->elem == i.element())
			{
				prim = prim->urm;
				delete p;
			}
			else
			{
				while (p->urm->elem != i.element())
				{
					p = p->urm;
				}
				PNod q = p->urm;
				p->urm = q->urm;
				delete q;
			}
		}
		else
		{
			i.decFrecventa(nr);
			frecv = nr;
		}
		dimensiune -= nr;
	}
	return frecv;
}

bool Colectie::sterge(TElem elem)
{
	if (cauta(elem))
	{
		IteratorColectie i = iterator();
		i.prim();
		while (i.element() != elem)
		{
			i.urmator();
		}
		i.decFrecventa(1);
		if (i.frecventa() == 0)
		{
			// IteratorColectie j = iterator();
			// j.prim();
			PNod p = prim;
			if (p->elem == i.element())
			{
				prim = prim->urm;
				delete p;
			}
			else
			{
				while (p->urm->elem != i.element())
				{
					p = p->urm;
				}
				PNod q = p->urm;
				p->urm = q->urm;
				delete q;
			}
		}
		dimensiune--;
		return true;
	}
	return false;
}

bool Colectie::cauta(TElem elem) const
{
	/* de adaugat */
	IteratorColectie i = iterator();
	i.prim();
	while (i.valid())
	{
		if (i.element() == elem)
			return true;
		i.urmator();
	}
	return false;
}

int Colectie::nrAparitii(TElem elem) const
{
	/* de adaugat */
	IteratorColectie i = iterator();
	i.prim();
	while (i.valid())
	{
		if (i.element() == elem)
			return i.frecventa();
		i.urmator();
	}
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
	IteratorColectie i(*this);
	i.prim();
	return i.valid() ? false : true;
}

IteratorColectie Colectie::iterator() const
{
	return IteratorColectie(*this);
}

Colectie::~Colectie()
{
	/* de adaugat */
	while (prim != nullptr)
	{
		PNod p = prim;
		prim = prim->urm;
		delete p;
	}
}
