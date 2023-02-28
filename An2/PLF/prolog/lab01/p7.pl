% 7.a. Sa se scrie un predicat care intoarce reuniunea a doua multimi.
% b. Sa se scrie un predicat care, primind o lista, intoarce multimea
% tuturor perechilor din lista.
% De ex, cu [a, b, c, d] va produce[[a, b], [a, c], [a, d], [b, c], [b,
% d], [c, d]].

% a)
% reuniuneAux(L1, Lcp, L2) = {   [], daca L1=[] si L2=[]   
%                        [L1.prim, reuniuneAux(L1.next,Lcp,L2)], daca L1 are elemente
%                        [L2.prim, reuniuneAux([],Lcp,L2.next)], daca L2.prim nu se afla in Lcp
%                        reuniuneAux([],Lcp,L2.next), altfel (L2.prim se afla in Lcp)
%                    }

% reuniuneAux(L1,Lcp,L2,R)
% L1 - lista 1 input
% Lcp - copie L1 in varianta initiala
% L2 - lista 2 input
% R - rezultat reuniune
% Model flux: (i,i,i,o),(i,i,i,i).
reuniuneAux([],_,[],[]).
reuniuneAux([H|T],Lcp,L2,[H|T2]):-
    reuniuneAux(T,Lcp,L2,T2).
reuniuneAux([],Lcp,[H|T],[H|T2]):-
    not(member(H,Lcp)),
    reuniuneAux([],Lcp,T,T2).
reuniuneAux([],Lcp,[H|T],T2):-
    member(H,Lcp),
    reuniuneAux([],Lcp,T,T2).

% reuniune(L1: lista, L2: lista, R: lista) - functie wrapper
% L1 - lista 1
% L2 - lista 2
% R - rezultat final
% Model flux: (i,i,o), (i,i,i)
reuniune(L1,L2,R):-
    reuniuneAux(L1,L1,L2,R).


% perechiSec(L,E) = {   [], daca L=[]
%                       [E,L.prim | perechiSec(L.next,E)], daca L nu e vid
% }

% perechiSec(L: lista, E: element, R: lista)
% L - lista initiala
% E - element cu care sa se creeze perechile
% R - rezultat perechi
% Model flux: (i,i,o), (i,i,i)
perechiSec([],_,[]).
perechiSec([H|T], E, [[E,H] | R]):-
    perechiSec(T,E,R).

% unire(L1 : lista, L2: lista, R: lista)
% L1 - lista initiala 1
% L2 - lista initiala 2
% R - lista care face append L2 la L1
% Model flux: (i,i,o),(i,i,i)
% unire(L1,L2) = {  [], daca L1=[] si L2=[]
%                   [L1.prim, unire(L1.next,L2)], L1 are elemente
%                   [L2.prim, unire(L1, L2.next)] altfel (L2 are elemente)
% }
unire([],[],[]).
unire([H|T],L2, [H|R]):-
    unire(T,L2,R).
unire([],[H|T],[H|R]):-
    unire([],T,R).
%                   
% b)
% perechi(L) = {    [], daca L=[]
%                   [], daca L=[A] (in caz ca)
%                   perechiSec(L.next, L.prim) U perechi(L.next), daca L=[A,B]

% perechi(L: lista, R: lista)
% L - lista initiala
% R - rezultat final
% Model flux: (i,o), (i,i)
perechi([],[]).
perechi([_],[]).
perechi([H|T],R2) :- 
    perechiSec(T,H,R),
    perechi(T,R1),
    % reuniune(R,R1,R2).
    reuniune(R,R1,R2).
    % findall([X,Y], (member(X,L), member(Y,L), X @< Y), R).
    % elementul special aici e faptul ca ni se cer perechile , nu neaparat ordonate in ordinea initiala din lista; atunci putem sa le consideram in ordine lexicografica (se aplica si pentru numere) si sigur vom trece prin toate distince (fiind <)



