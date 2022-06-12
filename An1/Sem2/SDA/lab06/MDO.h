#pragma once

#include <vector>
#include <algorithm>
#include <unordered_map>
typedef int TCheie;
typedef int TValoare;
typedef int TDisp;
#define NULLTVALOARE -1
#define maxDisp 20

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool (*Relatie)(TCheie, TCheie);
typedef struct nodCheie nodCheie;
struct nodCheie
{
	TCheie c;
	vector<TValoare> val;
	nodCheie *urm;
};

class MDO
{
	friend class IteratorMDO;

private:
	/* aici e reprezentarea */
	Relatie rel;
	nodCheie *dict[maxDisp];
	int dimensiune;

public:
	// constructorul implicit al MultiDictionarului Ordonat
	MDO(Relatie r);

	TDisp disp(TCheie) const;
	// adauga o pereche (cheie, valoare) in MDO
	void adauga(TCheie c, TValoare v);

	// cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;
	vector<TValoare> colectiaValorilor() const;
	TValoare ceaMaiFrecventaValoare() const;
	// sterge o cheie si o valoare
	// returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	// returneaza numarul de perechi (cheie, valoare) din MDO
	int dim() const;

	// verifica daca MultiDictionarul Ordonat e vid
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
	IteratorMDO iterator() const;

	// destructorul
	~MDO();
};
