#include "IteratorColectie.h"

IteratorColectie::IteratorColectie(const Colectie &c) : col(c)
{
	/* de adaugat */
	curent = col.prim;
	item = 1;
}

void IteratorColectie::prim()
{
	/* de adaugat */
	curent = col.prim;
	item = 1;
}

void IteratorColectie::urmator()
{
	/* de adaugat */
	if (valid())
	{
		if (item < frecventa())
		{
			item++;
		}
		else
		{
			curent = curent->urmator();
			item = 1;
		}
		return;
	}
	throw std::invalid_argument("The iterator is invalid! There is nowhere left to go.");
	// try
	// {
	// }
	// catch (std::invalid_argument &e)
	// {
	// 	// std::cout << e.what() << std::endl;
	// }
	// return;
}

bool IteratorColectie::valid() const
{
	/* de adaugat */
	return curent != nullptr;
}

TElem IteratorColectie::element() const
{
	/* de adaugat */
	if (valid())
	{
		return curent->getElement();
	}
	// try
	// {
	// }
	// catch (std::invalid_argument &e)
	// {
	// 	// std::cout << e.what() << std::endl;
	// 	return -1;
	// }
	throw std::invalid_argument("The current iterator is invalid!");
}
TElem IteratorColectie::frecventa() const
{
	return curent->getFrecventa();
}
void IteratorColectie::incFrecventa()
{
	curent->setFrecventa(curent->getFrecventa() + 1);
}
void IteratorColectie::decFrecventa(int nr)
{
	curent->setFrecventa(curent->getFrecventa() - nr);
}