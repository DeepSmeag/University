### Model based/driven development

### Model functional al unei aplicatii

### Limbaj modelare: UML

UML - limbaj formalizat ca sintaxa (sintaxa vizuala), dar nu semantic

# Etape a ciclului de dezvoltare

## I. Colectare a cerintelor

### Studiem modelul functional al unui sistem

- Diagrama UML a cazurilor de utilizare = instrumentul
- UML nu permite descrierea cazurilor de utilizare
- se folosesc sabloane de descriere a cazurilor de utilizare pt a compensa  

**La nivelul diagramei:**  

### 1. Caz de utilizare (CU)

- scenariu (relatia CU-scenariu similara clasa-obiect) = caz particular al unui CU, deci o instanta a CU  
*ex:* magazin online - plasarea unei comenzi  
- gandim scenariu principal/uzual (1 - accesare site; 2 - accesare produs specific; 3,4,5+ - interactiune cu buton comanda, introducere date comanda + metoda plata etc)
- trebuie gandit ca interactiune intre client si sistem; clientul face - sistemul raspunde; actiune / reactiune (ex: 1- clientul acceseaza site-ul; 2- sistemul prezinta site-ul; ....; x - clientul finalizeaza comanda; x+1 - sistemul cere date clientului etc.)
- gandim scenarii alternative (ex. introducere date gresite)
- **Reprezentare UML**  
  - (bula cu denumire "Plasare comanda")

  - (dreptunghi cu denumire "Plasare comanda" si un mic cerc intr-un colt; se numeste diagrama de CU)  
  ______________  
  |         O  |  
  |            |  
  |____________|  
  (deschide editorul)

### 2. Actori = rol jucat de o entitate externa sistemului

- interactioneaza cu sistemul
- **tipuri de actori**:  
  - instante umane vs. sisteme vs. dispozitive
  - activi vs. pasivi
  - principal (beneficiaza) vs. secundar
- **Reprezentare UML**
  - (stick figure) = client (indiferent ca e uman sau nu)
  - alternative pt usurinta (pictograma si diagrama de client)

### 3. Frontiera sistemului = system boundary / subject

- diagrama de sistem (un dreptunghi), contine CU in interior; conteaza sa delimiteze sistemul de exterior

### 4. Relatii

- intre actori si CU:
  - asociere (solid line)
- intre actori si actori:
  - generalizare (sageata cu triunghi la capat)
- intre CU si CU:
  - incluziune = dependenta (dotted line + sageata) <\<include\>>
  - extindere intre CU (dependenta, dotted line) <\<extend\>>
- generalizare (sageata cu triunghi la capat):
  - CU1 - Depunere numerar;
  - CU2 - Retragere numerar;
  - CU3 - Plata facturi;
  - CU4 - Interogare sold;  
  - **CU5 - Realizare tranzactie** -> toate celelalte CU sunt cazuri particulare ale lui CU5;  
   (CU5 **include** operatie de *Validare card* si **extinde** *Accesare HELP*)  
  
## II. Analiza cerintelor (CE)
  
### 1. Modelul conceptual al unui sistem

// Identificarea proceselor se face din cazurile de utilizare
- identificam concepte (substantive) din domeniul problemei (ex: comanda, fel de mancare, client, restaurant, etc.)
- toate conceptele devin clase
- responsabilitatile (actiuni / procese) nu devin clase
- nu reprezentam ca atribute ceea ce nu e elementar
- *modelul conceptual* se afiseaza in diagrama de clase, dar nu este strict reprezentat prin ea: reprezentam *tipuri de date*, *clase = concepte*, *relatii = asocieri* intre concepte, **atributele care nu apartin unei clase (ex cantitate mancare comandata) se scot intr-un concept diferit** (linie punctata intre cele 2 concepte legate)
- (optional dar recomandat) se adnoteaza = numesc asocierile (ex: un order=comanda -*--refera--1..*- dishuri=feluri de mancare)
- se scriu rolurile (ex: comanda _care refera_, feluri de mancare _referite_); alt ex: waiter=autor/creator de comanda -1--delivers--0..*- orders=delivered orders


### Keywords

- Requirements presentation
- Model descriptiv, prospectiv

