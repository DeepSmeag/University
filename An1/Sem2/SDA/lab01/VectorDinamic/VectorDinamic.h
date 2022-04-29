// tip de data generic (pentru moment este intreg)
#include <stdio.h>
typedef int TElem;

class Iterator;
class VectorDinamic
{

private:
	// capacitate
	int capacity;
	// dimensiune
	int n;
	// elemente
	TElem *e;
	// pentru redimensionare
	void redim();

public:
	// pentru ca Iteratorul sa poata accesa reprezentarea vectorului
	friend class Iterator;

	// constructor
	VectorDinamic(int capacity);

	// destructor
	~VectorDinamic();

	// dimensiunea vectorului (numar de elemente)
	int dim() const;

	// elementul al i-lea
	TElem element(int) const;

	// adaugare la sfarsit
	void adaugaSfarsit(TElem);

	// returneaza un iterator pe vector
	Iterator iterator();

	// alte operatii specifice
	bool stergereElem(TElem);
	// constructor copiere, operator atribuire...
	bool cauta(TElem);

	int getIndex(TElem);

	void print();
};

class Iterator
{

private:
	// iteratorul contine o referinta catre container
	const VectorDinamic &v;

	// pozitia curenta in iterator
	int curent;

	// varianta 2
	// TElem *curent; //pointer spre un element din vector

public:
	// constructor
	Iterator(VectorDinamic &);

	// operatii pe iterator
	void prim();

	void urmator();

	TElem element() const;

	int index() const;

	bool valid() const;

	void shiftToLeft();

	void emptyCurrent();

	bool compareCurrentToElement(TElem elem) const;

	void changeCurrentValue(TElem newValue);
};
