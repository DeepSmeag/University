#include "Matrice.h"

#include <exception>

using namespace std;
// linie coloana valoare
// ordinea de gasire <linie, coloana>
// linie apoi coloana
bool Matrice::rel(int l1, int c1, int l2, int c2) const
{
	if (l1 < l2 || (l1 == l2 && c1 < c2))
	{
		return true;
	}
	return false;
}
bool Matrice::isValid(node n) const
{
	if (n.linie < 0 || n.coloana < 0 || n.valoare == NULL_TELEMENT)
	{
		return false;
	}
	return true;
}
Matrice::Matrice(int m, int n) : nodes(new node[maxElems]), primLiber{0}
{
	if (m <= 0 || n <= 0)
	{
		throw exception();
	}
	nrLines = m;
	nrCols = n;
	for (int i = 0; i < maxElems; i++)
	{
		nodes[i].linie = nodes[i].coloana = -1;
		nodes[i].valoare = NULL_TELEMENT;
		nodes[i].stanga = i + 1;
	}
	nodes[maxElems - 1].stanga = NULL_TELEMENT;
}

int Matrice::nrLinii() const
{
	return nrLines;
}

int Matrice::nrColoane() const
{
	return nrCols;
}

// CT: theta(h) - h=inaltimea arborelui
TElem Matrice::element(int i, int j) const
{
	// caut elementul de la linia i si j
	int ind = 0;
	if (i < 0 || j < 0)
	{
		throw exception();
	}
	// cat timp caut in elemente care exista
	while (nodes[ind].valoare != NULL_TELEMENT && !(nodes[ind].linie == i && nodes[ind].coloana == j))
	{
		bool stanga = rel(i, j, nodes[ind].linie, nodes[ind].coloana);
		// stanga sau dreapta, cand a dat peste linia si coloana respectiva se opreste din while
		if (stanga)
		{
			if (nodes[ind].stanga == NULLCHILD)
			{
				ind = primLiber;
				break;
			}
			ind = nodes[ind].stanga;
		}
		else
		{
			if (nodes[ind].dreapta == NULLCHILD)
			{
				ind = primLiber;
				break;
			}
			ind = nodes[ind].dreapta;
		}
	}

	return nodes[ind].valoare;
}

// CT: theta(h)
TElem Matrice::modifica(int i, int j, TElem e)
{
	int ind = 0, antInd;

	if (i < 0 || j < 0)
	{
		throw exception();
	}
	if (e != NULL_TELEMENT)
	{

		bool stanga = false;
		// cat timp caut in elemente care exista
		while (nodes[ind].valoare != NULL_TELEMENT && !(nodes[ind].linie == i && nodes[ind].coloana == j))
		{
			stanga = rel(i, j, nodes[ind].linie, nodes[ind].coloana);
			// stanga sau dreapta, cand a dat peste linia si coloana respectiva se opreste din while
			antInd = ind;
			if (stanga)
			{
				if (nodes[ind].stanga == NULLCHILD)
				{
					ind = primLiber;
					break;
				}
				ind = nodes[ind].stanga;
			}
			else
			{
				if (nodes[ind].dreapta == NULLCHILD)
				{
					ind = primLiber;
					break;
				}
				ind = nodes[ind].dreapta;
			}
		}
		int oldVal = nodes[ind].valoare;
		if (ind == primLiber)
		{
			// ceva trebuie initializat
			nodes[ind].valoare = e;
			nodes[ind].linie = i;
			nodes[ind].coloana = j;
			primLiber = nodes[primLiber].stanga;
			nodes[ind].stanga = NULLCHILD;
			nodes[ind].dreapta = NULLCHILD;
			if (ind != 0)
			{
				if (stanga)
				{
					nodes[antInd].stanga = ind;
				}
				else
				{
					nodes[antInd].dreapta = ind;
				}
			}
		}
		else
		{
			// a fost gasit, trebuie schimbata doar valoarea
			nodes[ind].valoare = e;
		}
		return oldVal;
	}
	else // caz de stergere
	{
		// primLiber se duce mai departe la alocare nod nou (nu e cazul ca aici stergem)
		// primLiber = nodes[primLiber].stanga;
		// cazul include si radacina
		int ind = 0;
		int oldVal = NULL_TELEMENT;
		while (nodes[ind].valoare != NULL_TELEMENT && !(nodes[ind].linie == i && nodes[ind].coloana == j))
		{
			bool stanga = rel(i, j, nodes[ind].linie, nodes[ind].coloana);
			// stanga sau dreapta, cand a dat peste linia si coloana respectiva se opreste din while
			antInd = ind;
			if (stanga)
			{
				if (nodes[ind].stanga == NULLCHILD)
				{
					ind = primLiber;
					break;
				}
				ind = nodes[ind].stanga;
			}
			else
			{
				if (nodes[ind].dreapta == NULLCHILD)
				{
					ind = primLiber;
					break;
				}
				ind = nodes[ind].dreapta;
			}
		}
		if (nodes[ind].coloana == j && nodes[ind].linie == i)
		{
			int radNoua;
			// daca are doar 1 copil, copilul devine radacina
			if (nodes[ind].stanga != NULLCHILD && nodes[ind].dreapta == NULLCHILD)
			{
				radNoua = nodes[ind].stanga;
			}
			else if (nodes[ind].stanga == NULLCHILD && nodes[ind].dreapta != NULLCHILD)
			{
				radNoua = nodes[ind].dreapta;
			}
			// daca are 2 copii, trebuie sa fac algoritmul de cautare minim
			else
			{
				int indCurent = nodes[ind].dreapta;
				while (nodes[indCurent].stanga != NULLCHILD)
				{
					indCurent = nodes[indCurent].stanga;
				}
				// daca mai are totusi un copil, trebuie sa fac si acolo schimbari
				radNoua = indCurent;
				if (nodes[indCurent].dreapta != NULLCHILD)
				{
					// indCurent trebuie sa fie replaced de copilul din dreapta
					// copilul din dreapta trebuie sa fie eliberat si sa primeasca primLiber undeva colo
					// considerand codul de mai jos, varianta decenta ar fi sa dau switch la indCurent.dreapta si indCurent si sa dau radNoua=indCurent.dreapta
					node tmp;
					tmp.coloana = nodes[indCurent].coloana;
					tmp.linie = nodes[indCurent].linie;
					tmp.valoare = nodes[indCurent].valoare;
					tmp.stanga = nodes[indCurent].stanga;
					tmp.dreapta = nodes[indCurent].dreapta;

					int newCurent = nodes[indCurent].dreapta;
					nodes[indCurent].coloana = nodes[newCurent].coloana;
					nodes[indCurent].linie = nodes[newCurent].linie;
					nodes[indCurent].valoare = nodes[newCurent].valoare;
					nodes[indCurent].stanga = nodes[newCurent].stanga;
					nodes[indCurent].dreapta = nodes[newCurent].dreapta;

					nodes[newCurent].coloana = tmp.coloana;
					nodes[newCurent].linie = tmp.linie;
					nodes[newCurent].stanga = tmp.stanga;
					nodes[newCurent].dreapta = tmp.dreapta;
					nodes[newCurent].valoare = tmp.valoare;

					radNoua = newCurent;
				}
			}
			// radNoua este indicele de unde trebuie sa schimb cu radacina (nodul de unde trebuie sa sterg), adica la indicele 0
			// trebuie sa copiez informatia din nodes[radNoua] in nodes[ind]
			oldVal = nodes[ind].valoare;
			nodes[ind].valoare = nodes[radNoua].valoare;
			nodes[ind].linie = nodes[radNoua].linie;
			nodes[ind].coloana = nodes[radNoua].coloana;
			nodes[ind].stanga = nodes[radNoua].stanga;
			nodes[ind].dreapta = nodes[radNoua].dreapta;
			// apoi nodes[radNoua] trebuie reinitializat sa fie cu totul nul
			nodes[radNoua].valoare = NULL_TELEMENT;
			nodes[radNoua].dreapta = NULLCHILD;
			nodes[radNoua].stanga = NULLCHILD;
			nodes[radNoua].linie = -1;
			nodes[radNoua].coloana = -1;
			// apoi pun nodes[radNoua].stanga = primLiber
			nodes[radNoua].stanga = primLiber;
			// si apoi primLiber = radNoua;
			primLiber = radNoua;
		}
		return oldVal;
	}
}

// CT: theta(linii*col)
// determina suma elementelor de pe o linia j
TElem Matrice::suma(int j)
{
	/**
	 * s<- 0 // suma
	 * pentru i<-0,nrLinii*nrColoane
	 * 	daca nod[i].linie = j
	 * 		s<-s+nod[i].valoare
	 * 	sfdaca
	 * sfpentru
	 * returneaza s
	 */
	TElem s = 0;
	for (int i = 0; i < nrLines * nrCols; i++)
	{
		if (nodes[i].linie == j)
		{
			s += nodes[i].valoare;
		}
	}
	return s;
}
