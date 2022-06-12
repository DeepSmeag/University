#pragma once
#include <iostream>
typedef int TElem;

#define NULL_TELEMENT 0
#define maxElems 999999
#define NULLCHILD -1
typedef struct
{
	int linie, coloana;
	TElem valoare;
	int stanga, dreapta;
} node;

class Matrice
{

private:
	/* aici e reprezentarea */
	int nrLines, nrCols;
	node *nodes;
	int primLiber;
	// cand un nod nu are copil stanga/dreapta acele fielduri vor fi -1

public:
	// constructor
	// se arunca exceptie daca nrLinii<=0 sau nrColoane<=0
	Matrice(int nrLinii, int nrColoane);

	// destructor
	~Matrice() { delete[] nodes; };

	// returnare element de pe o linie si o coloana
	// se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
	// indicii se considera incepand de la 0
	TElem element(int i, int j) const;

	bool rel(int l1, int c1, int l2, int c2) const;
	bool isValid(node n) const;
	// returnare numar linii
	int nrLinii() const;

	// determina suma elementelor de pe o linia j
	TElem suma(int j);

	// returnare numar coloane
	int nrColoane() const;

	// modificare element de pe o linie si o coloana si returnarea vechii valori
	// se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
	TElem modifica(int i, int j, TElem);
};
