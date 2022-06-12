#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

MDO::MDO(Relatie r) : rel{r}, dict{}, dimensiune(0)
{
	for (int i = 0; i < maxDisp; i++)
	{
		dict[i] = NULL;
	}
}

TDisp MDO::disp(TCheie c) const
{
	return abs(c % maxDisp);
}

void MDO::adauga(TCheie c, TValoare v)
{
	TDisp ind = disp(c);
	if (dict[ind] == NULL)
	{
		dict[ind] = new nodCheie;
		dict[ind]->c = c;
		dict[ind]->val.push_back(v);
		dict[ind]->urm = NULL;
		dimensiune++;
		return;
	}
	nodCheie *tmpNode = dict[ind];
	nodCheie *antNode = NULL;
	while (tmpNode != NULL && rel(tmpNode->c, c) == true)
	{
		if (tmpNode->c == c)
		{
			tmpNode->val.push_back(v);
			dimensiune++;
			return;
		}
		antNode = tmpNode;
		tmpNode = tmpNode->urm;
	}
	// nu a gasit cheia deci adaug alta
	// trebuie bagata intre antNode si tmpNode
	nodCheie *newNode = new nodCheie;
	newNode->c = c;
	newNode->val.push_back(v);
	dimensiune++;
	// Daca am ajuns la final si se adauga in coada tmpNode va fi NULL deci se va face o adaugare buna
	newNode->urm = tmpNode;
	if (antNode != NULL)
	{
		antNode->urm = newNode;
	}
}

vector<TValoare> MDO::cauta(TCheie c) const
{
	TDisp ind = disp(c);
	nodCheie *tmpNode = dict[ind];
	while (tmpNode != NULL && rel(tmpNode->c, c) == true)
	{
		if (tmpNode->c == c)
		{
			return tmpNode->val;
		}
		tmpNode = tmpNode->urm;
	}
	return vector<TValoare>();
}

bool MDO::sterge(TCheie c, TValoare v)
{
	TDisp ind = disp(c);
	nodCheie *tmpNode = dict[ind];
	while (tmpNode != NULL && rel(tmpNode->c, c) == true)
	{
		if (tmpNode->c == c)
		{
			auto posStart = std::remove(tmpNode->val.begin(), tmpNode->val.end(), v);
			if (posStart == tmpNode->val.end())
			{
				return false;
			}
			tmpNode->val.erase(posStart, tmpNode->val.end());
			dimensiune--;
			return true;
		}
		tmpNode = tmpNode->urm;
	}
	return false;
}
// CF: theta(n) CD: theta(n), CM: theta(n), CT: theta(n) - n = nr. valorilor din dictionar
vector<TValoare> MDO::colectiaValorilor() const
{
	/**
	 * Fie vect : vector<TValoare>
	 * pentru it<-dict[0..nrMaxDispersii]
	 * 	copie <- it
	 * 	cat timp copie != NULL executa
	 * 		pentru itVal <- (cp->val[0]..cp->val[size - 1])
	 * 			vect.adauga_coada(itVal) //itVal este iterator pentru valori
	 * 			.// va tine valorile in sine
	 * 		sfpentru
	 * 		copie <- copie->urm
	 * 	sfcattimp
	 * sfpentru
	 *
	 * **In esenta ideea este ca trecem prin toate valorile din dictionar
	 * Si le adaugam la vectorul de valori
	 *
	 */
	vector<TValoare> vect;
	for (auto &it : dict)
	{
		auto cp = it;
		while (cp != NULL)
		{
			for (auto &itVal : cp->val)
			{
				vect.push_back(itVal);
			}
			cp = cp->urm;
		}
	}
	return vect;
}
// CF: theta(n) CD: theta(n), CM: theta(n), CT: theta(n) - n = nr. valorilor din dictionar
TValoare MDO::ceaMaiFrecventaValoare() const
{
	/**
	 * Cream un dictionar temporar de tip <TValoare, int>, unde int - frecventa valorii
	 * pentru it<-dict[0..nrMaxDispersii]
	 * 	copie <- it
	 * 	cat timp copie != NULL executa
	 * 		pentru itVal <- (cp->val[0]..cp->val[size - 1])
	 * 			daca map.find(itVal) != mappy.end() //daca exista valoarea deja
	 * 				.//crestem frecventa
	 * 				mappy[itVal]++;
	 * 			altfel
	 * 				mappy.insert(pereche(itVal, 1))
	 * 				.//inseram perechea <valoare, frecventa>
	 * 			sfdaca
	 * 		sfpentru
	 * 		copie <- copie->urm
	 * 	sfcattimp
	 * sfpentru
	 * daca mappy.size() == 0 //daca este goala
	 * 	return NULLTVALOARE
	 * altfel
	 * 	maxap: int
	 * 	val: TValoare
	 * 	pentru it<- mappy.iterator()
	 * 		daca it.second > maxap
	 * 			maxap <- it.second
	 * 			val<- it.first
	 * 		sfdaca
	 * 	sfpentru
	 * 	return val
	 * sfdaca
	 */
	unordered_map<TValoare, int> mappy;
	for (auto &it : dict)
	{
		auto cp = it;
		while (cp != NULL)
		{
			for (auto &itVal : cp->val)
			{
				if (mappy.find(itVal) != mappy.end())
				{
					mappy[itVal]++;
				}
				else
				{
					mappy.insert(make_pair(itVal, 1));
				}
			}
			cp = cp->urm;
		}
	}
	if (mappy.size() == 0)
	{
		return NULLTVALOARE;
	}
	else
	{
		int maxap = 0;
		TValoare val;
		for (auto &it : mappy)
		{
			if (it.second > maxap)
			{
				val = it.first;
				maxap = it.second;
			}
		}
		return val;
	}
}

int MDO::dim() const
{
	/* de adaugat */
	return dimensiune;
}

bool MDO::vid() const
{
	return dimensiune == 0;
}

IteratorMDO MDO::iterator() const
{
	return IteratorMDO(*this);
}

MDO::~MDO()
{
	for (int i = 0; i < maxDisp; i++)
	{
		while (dict[i] != NULL)
		{
			nodCheie *nextNode = dict[i]->urm;
			free(dict[i]);
			dict[i] = nextNode;
		}
	}
}
