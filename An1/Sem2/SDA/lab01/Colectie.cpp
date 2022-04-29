
#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;
// Colectie::Colectie()
// {
// 	/**
// 	 * @brief
// 	 *
// 	 */
// }
Colectie::Colectie() : D(16), P(16)
{
}
void Colectie::printAll()
{
	this->D.print();
	std::cout << endl;
	this->P.print();
	std::cout << endl;
}
void Colectie::adauga(TElem elem)
{
	/* de adaugat */
	/**
	 * @brief In D sunt valori distince; Daca elem se afla in D, atunci in P se va adauga un elem cu pozitia coresp in D
	 * Daca nu se afla in D, atunci se adauga in D si in P va fi adaugata pozitia corespunzatoare din D
	 *
	 */
	if (cauta(elem))
	{
		int index = this->D.getIndex(elem);
		this->P.adaugaSfarsit(index);
	}
	else
	{
		this->P.adaugaSfarsit(this->D.dim());
		this->D.adaugaSfarsit(elem);
	}
}

bool Colectie::sterge(TElem elem)
{
	/* de adaugat */
	/**
	 * @brief Daca nu se afla in D, return False
	 * Daca se afla in D, stergem prima aparitie a indexului corespunzator din P, iar apoi verificam daca se mai afla indexul in P; daca nu se afla, stergem si din D si returnam True
	 *
	 */
	if (cauta(elem))
	{
		// gasim index din D ca sa il stergem din P
		int index;
		IteratorColectie i = this->iterator();
		i.prim();
		index = this->D.getIndex(elem);
		this->P.stergereElem(index);

		// verificam daca mai exista in P indexul
		if (!(this->P.cauta(index)))
		{
			this->D.stergereElem(elem);
			Iterator p = this->P.iterator();
			while (p.valid())
			{
				if (p.element() > index)
				{
					p.changeCurrentValue(p.element() - 1);
				}
				p.urmator();
			}
		}
		return true;
	}
	return false;
}

bool Colectie::cauta(TElem elem) const
{
	/* de adaugat */
	/**
	 * @brief Cautam daca se afla in D
	 *
	 */
	IteratorColectie i = this->iterator();
	i.prim();
	while (i.valid())
	{
		if (i.element() == elem)
		{
			return true;
		}
		i.urmator();
	}

	return false;
}

int Colectie::nrAparitii(TElem elem) const
{
	/* de adaugat */
	/**
	 * @brief Daca se afla in D, cautam de cate ori apare indexul corespunzator in P
	 * Daca nu se afla in D, return 0
	 *
	 */
	int nrAp = 0;
	if (cauta(elem) == true)
	{
		IteratorColectie i = this->iterator();
		i.prim();
		while (i.valid())
		{
			if (i.element() == elem)
			{
				nrAp++;
			}
			i.urmator();
		}
	}
	return nrAp;
}

int Colectie::dim() const
{
	/* de adaugat */
	/**
	 * @brief Return len(P)
	 *
	 */

	return this->P.dim();
}

bool Colectie::vida() const
{
	/* de adaugat */
	/**
	 * @brief Daca len(P) == 0 return True
	 * Else return False
	 *
	 */
	return this->P.dim() == 0 ? true : false;
}

IteratorColectie Colectie::iterator() const
{
	return IteratorColectie(*this);
}

Colectie::~Colectie()
{
	/* de adaugat */
	/**
	 * @brief Destructor D si P de tip VectorDinamic
	 *
	 */
	// delete[] & (this->D);
	// delete[] & (this->P);
}
