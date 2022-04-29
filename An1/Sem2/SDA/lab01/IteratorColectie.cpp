#include "IteratorColectie.h"
#include "Colectie.h"

IteratorColectie::IteratorColectie(const Colectie &c) : col(c)
{
	/* de adaugat */
	curent = 0;
}

void IteratorColectie::prim()
{
	/* de adaugat */
	/**
	 * @brief Se va reseta ca in starea din constructor
	 *
	 */
	curent = 0;
}

void IteratorColectie::urmator()
{
	/* de adaugat */
	/**
	 * @brief Urmatorul element din componentele vectori
	 *
	 */
	curent++;
}

bool IteratorColectie::valid() const
{
	/* de adaugat */
	/**
	 * @brief Va fi valid daca nu a trecut de ultimul element al Colectiei
	 *
	 */
	return curent < this->col.P.dim() ? true : false;
}

TElem IteratorColectie::element() const
{
	/* de adaugat */
	/**
	 * @brief Obtinere valoare a elementului
	 *
	 */
	// iau valoarea curenta din P si cu val aia caut in D (ca index)
	int elem = this->col.P.element(curent);
	return this->col.D.element(elem);
}
