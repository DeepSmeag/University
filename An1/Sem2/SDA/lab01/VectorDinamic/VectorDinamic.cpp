#include "VectorDinamic.h"

void VectorDinamic::redim()
{

	// alocam un spatiu de capacitate dubla
	TElem *eNou = new int[2 * capacity];

	// copiem vechile valori in noua zona
	for (int i = 0; i < n; i++)
		eNou[i] = e[i];

	// dublam capacitatea
	capacity = 2 * capacity;

	// eliberam vechea zona
	delete[] e;

	// vectorul indica spre noua zona
	e = eNou;
}

VectorDinamic::VectorDinamic(int capacity)
{
	// setam capacitatea
	this->capacity = capacity;

	// alocam spatiu de memorare pentru vector
	e = new TElem[capacity];

	// setam numarul de elemente
	this->n = 0;
}

VectorDinamic::~VectorDinamic()
{
	// eliberam zona de memorare alocata vectorului
	delete[] e;
}

int VectorDinamic::dim() const
{
	return n;
}

TElem VectorDinamic::element(int i) const
{
	return e[i];
}

void VectorDinamic::adaugaSfarsit(TElem e)
{
	// daca s-a atins capacitatea maxima, redimensionam
	if (n == capacity)
		redim();

	// adaugam la sfarsit
	this->e[n++] = e;
}
bool VectorDinamic::stergereElem(TElem e)
{
	Iterator i = iterator();
	i.prim();
	int gasit = false;
	while (i.valid() && !gasit)
	{
		if (i.compareCurrentToElement(e) == true)
			gasit = true;
		i.urmator();
	}
	while (i.valid())
	{
		i.shiftToLeft();
		i.urmator();
	}
	if (gasit)
	{
		n--;
	}
	return gasit;
}
int VectorDinamic::getIndex(TElem e)
{
	Iterator i = iterator();
	i.prim();
	while (i.valid())
	{
		if (i.element() == e)
		{
			return i.index();
		}
		i.urmator();
	}
	return -1;
}
bool VectorDinamic::cauta(TElem e)
{
	Iterator i = iterator();
	i.prim();
	while (i.valid())
	{
		if (i.element() == e)
			return true;
		i.urmator();
	}
	return false;
}
void VectorDinamic::print()
{
	Iterator i = iterator();
	i.prim();
	while (i.valid())
	{
		printf("%d ", i.element());
		i.urmator();
	}
}
Iterator VectorDinamic::iterator()
{
	// returnam un iterator pe vector
	return Iterator(*this);
}

Iterator::Iterator(VectorDinamic &_v) : v(_v)
{
	// seteaza iteratorul pe prima pozitie din vector
	curent = 0;

	// varianta 2
	// curent=v.e; //- pointer spre primul element din vector
}

void Iterator::prim()
{
	// seteaza iteratorul pe prima pozitie din vector
	curent = 0;

	// varianta 2
	// curent=v.e; //- pointer spre primul element din vector
}

bool Iterator::valid() const
{
	// verifica daca iteratorul indica spre un element al vectorului
	return curent < v.dim();

	// varianta 2
	// return curent-v.e<v.dim();
}

TElem Iterator::element() const
{
	// elementul curent
	return v.e[curent];

	// varianta 2
	// return *curent;
}

void Iterator::urmator()
{
	// pentru ambele variante
	curent++;
}

int Iterator::index() const
{
	return curent;
}

void Iterator::shiftToLeft()
{
	v.e[curent - 1] = v.e[curent];
}
void Iterator::emptyCurrent()
{
	v.e[curent] = 0;
}
bool Iterator::compareCurrentToElement(TElem elem) const
{
	return v.e[curent] == elem ? true : false;
}
void Iterator::changeCurrentValue(TElem newValue)
{
	v.e[curent] = newValue;
}