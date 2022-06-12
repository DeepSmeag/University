#include "IteratorMDO.h"
#include "MDO.h"
// CT : theta(n+m)
IteratorMDO::IteratorMDO(const MDO &d) : dict(d), nodValIndice(0), nodCrt{NULL}, nodPrim{NULL}
{
	bool gata = true;
	// voi lua adresele ca sa trec prin ele;
	// nu sunt copii de fapt, sunt referintele nodurilor originale;
	nodCheie *dictCopii[maxDisp];
	for (int i = 0; i < maxDisp; i++)
	{
		dictCopii[i] = dict.dict[i];
	}
	nodCheie *nodCurent;
	// O initializare originala ca sa pornim de undeva
	// Daca dict e gol atunci nu exista spatiu alocat pentru iterator
	// spatiul se va aloca pe masura ce se face si merge-ul
	// merge-ul se face gradual, pornim de la dictCopii[0] si facem merge succesiv cu celelalte
	int startingDisp = 0;
	for (int startingDisp = 0; startingDisp < maxDisp; startingDisp++)
	{
		if (dictCopii[startingDisp] != NULL)
		{
			nodCheie *nodDict = dictCopii[startingDisp];
			nodCurent = new nodCheie;
			nodCurent->c = nodDict->c;
			nodCurent->urm = NULL;
			nodCurent->val = nodDict->val;
			nodPrim = nodCurent;
			nodDict = nodDict->urm;
			while (nodDict != NULL)
			{
				nodCheie *newNode = new nodCheie;
				newNode->c = nodDict->c;
				newNode->urm = NULL;
				newNode->val = nodDict->val;
				nodCurent->urm = newNode;
				nodCurent = newNode;
				nodDict = nodDict->urm;
			}
			break;
		}
	}

	for (int i = startingDisp + 1; i < maxDisp; i++)
	{
		nodCurent = nodPrim;
		if (dictCopii[i] != NULL)
		{
			nodCheie *nodDict = dictCopii[i];
			// 241 cu 247
			// in cazul in care trebuie bagat ca primul element
			if (!dict.rel(nodCurent->c, nodDict->c))
			{
				// std::cout << nodCurent->c << " " << nodDict->c << std::endl;
				nodCheie *newNode = new nodCheie;
				newNode->c = nodDict->c;
				newNode->urm = nodCurent;
				newNode->val = nodDict->val;
				nodPrim = newNode;
				// nodCurent = nodPrim;
				nodDict = nodDict->urm;
			}
			// continuam, acum sigur nu e primul element
			nodCheie *antNode = nodPrim;
			// ideea e mereu sa introduc noduri din dictCopii intre antNode si nodCurent

			while (nodDict != NULL && nodCurent != NULL)
			{
				while (nodDict != NULL && !dict.rel(nodCurent->c, nodDict->c))
				{
					// bag nodul din dictCopii intre antNode si nodCurent
					nodCheie *newNode = new nodCheie;
					newNode->c = nodDict->c;
					newNode->val = nodDict->val;
					newNode->urm = nodCurent;
					antNode->urm = newNode;
					antNode = newNode;
					nodDict = nodDict->urm;
				}
				antNode = nodCurent;
				nodCurent = nodCurent->urm;
			}
			// ne pasa doar daca dictCopii nu a ajuns la final
			while (nodDict != NULL)
			{
				// am ajuns la finalul nodCurent deci de aici totul se adauga in coada, se foloseste antNode
				nodCheie *newNode = new nodCheie;
				newNode->c = nodDict->c;
				newNode->val = nodDict->val;
				newNode->urm = NULL;
				antNode->urm = newNode;
				nodDict = nodDict->urm;
				antNode = newNode;
			}
		}
	}
	// nodCurent = nodPrim;
	// while (nodCurent != NULL)
	// {
	// 	std::cout << nodCurent->c << std::endl;
	// 	nodCurent = nodCurent->urm;
	// }
	nodCrt = nodPrim;
}

void IteratorMDO::prim()
{
	/* de adaugat */
	nodValIndice = 0;
	nodCrt = nodPrim;
}

void IteratorMDO::urmator()
{
	if (nodValIndice == nodCrt->val.size() - 1)
	{
		nodValIndice = 0;
		nodCrt = nodCrt->urm;
		return;
	}
	nodValIndice++;
}

bool IteratorMDO::valid() const
{
	/* de adaugat */
	return nodCrt != NULL;
}

TElem IteratorMDO::element() const
{
	/* de adaugat */
	if (!valid())
		throw exception();
	return pair<TCheie, TValoare>(nodCrt->c, nodCrt->val[nodValIndice]);
}
