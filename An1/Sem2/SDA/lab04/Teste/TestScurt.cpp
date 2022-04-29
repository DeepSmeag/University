#include "TestScurt.h"
#include <assert.h>
#include "../Colectie.h"
#include "../IteratorColectie.h"

void testAll()
{ // apelam fiecare functie sa vedem daca exista
	Colectie c;
	assert(c.vida() == true);
	assert(c.dim() == 0);
	// adaug niste elemente
	c.adauga(5);
	c.adauga(1);
	c.adauga(10);
	c.adauga(7);
	c.adauga(1);
	c.adauga(11);
	c.adauga(-3);
	c.adauga(20);
	assert(c.dim() == 8);
	assert(c.cauta(10) == true);
	assert(c.cauta(16) == false);
	assert(c.nrAparitii(1) == 2);
	assert(c.nrAparitii(7) == 1);
	assert(c.sterge(1) == true);
	assert(c.sterge(6) == false);
	assert(c.dim() == 7);
	assert(c.nrAparitii(1) == 1);

	assert(c.sterge(20) == true);
	assert(c.sterge(-3) == true);
	assert(c.sterge(11) == true);
	assert(c.sterge(7) == true);
	assert(c.sterge(10) == true);
	assert(c.sterge(1) == true);
	assert(c.sterge(5) == true);

	IteratorColectie ic = c.iterator();
	ic.prim();
	while (ic.valid())
	{
		TElem e = ic.element();
		ic.urmator();
	}
	assert(c.nrAparitii(99) == 0);
	c.adaugaAparitiiMultiple(4, 99);
	assert(c.cauta(99) == true);
	assert(c.nrAparitii(99) == 4);
	try
	{
		c.adaugaAparitiiMultiple(-1, 99);
		assert(false);
	}
	catch (std::invalid_argument &exception)
	{
		exception.what();
	}
	// assert(c.eliminaAparitii(3, 99) == 3);
	// assert(c.nrAparitii(99) == 1);
	// assert(c.eliminaAparitii(2, 99) == 1);
	// assert(c.nrAparitii(99) == 0);
}