% 12. Se da sirul a1,...,anformat din numere intregi distincte. Se cere sa se afiseze  toate  submultimile  cu  aspect  de  "munte"
% (o secvență se  spune  ca  are aspect de "munte" daca elementele cresc pana la un moment dat, apoi descresc. 
% De ex. 10 16 27 18 14 7).
% [3,2,1,4,6,8,7,5,10,20,15,17,16,13,12,11]
% [1,4,6,8,7,5],[5,10,20,15],[15,17,16,13,12,11]
% OBS: submultimi -> nu trebuie sa pastrez ordinea din multimea initiala
% [1,3,2],[1,3,4,2] ... sunt multe;
% Folosesc test mai mic pentru a verifica


% candidate(L:list, E:element)
% candidate(i,o),(i,i)
candidate([H|_], H).
candidate([_|T], E) :- candidate(T, E).


% mountains(L:list, F:integer, Col:list, R:list)
% mountains(i,i,i,o)
mountains(_, 0, Col, Col).
mountains(L, 1, [H|Col], R) :- 
    candidate(L, E),
    E > H, 
    mountains(L, 1, [E|[H|Col]], R).
mountains(L, _, [H|Col], R) :- 
    candidate(L, E),
    E < H, 
    not(candidate(Col, E)),
    mountains(L, 0, [E|[H|Col]], R).

% principal(L:list, R:list)
% principal(i,i)-det,(i,o)-non-det

principal(L, R) :- 
    candidate(L, E1),
    candidate(L, E2),
    E1 > E2,
    mountains(L, 1, [E1,E2], R).


