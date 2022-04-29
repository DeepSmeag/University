#include "IteratorColectie.h"

IteratorColectie::IteratorColectie(const Colectie &c) : col(c), curentIndice(col.prim), item(1)
{
}

void IteratorColectie::prim()
{
	/* de adaugat */
	curentIndice = col.prim;
	item = 1;
}

void IteratorColectie::urmator()
{
	if (valid())
	{
		if (item < frecventa())
		{
			item++;
		}
		else
		{

			curentIndice = col.urm[curentIndice];
			item = 1;
		}
		return;
	}
	throw std::invalid_argument("The iterator is invalid! There is nowhere left to go.");
}

bool IteratorColectie::valid() const
{
	/* de adaugat */
	return curentIndice != NIL;
}

TElem IteratorColectie::element() const
{
	if (valid())
	{
		return col.elems[curentIndice];
	}
	throw std::invalid_argument("The current iterator is invalid!");
}
TElem IteratorColectie::frecventa() const
{
	return col.frecventa[curentIndice];
}
void IteratorColectie::incFrecventa(int nr)
{
	col.frecventa[curentIndice] += nr;
}
void IteratorColectie::decFrecventa(int nr)
{
	col.frecventa[curentIndice] -= nr;
}