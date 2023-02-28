%  a) Sa se determine pozitiile elementului maxim dintr-o lista liniara. De ex:poz([10,14,12,13,14], L) va produce L = [2,5].
%  b)Se  da  o  lista  eterogena,  formata  din  numere  intregi  si  liste  de  numere intregi. 
%  Sa se inlocuiasca fiecare sublista cu pozitiile elementului maxim din sublista respectiva. 
%  De ex:[1, [2, 3], [4, 1, 4], 3, 6, [7, 10, 1, 3, 9], 5, [1, 1, 1], 7] 
%  =>[1, [2], [1, 3], 3, 6, [2], 5, [1, 2, 3], 7]


% Model matematic
% detmaxelem(L) = { -1, n = 0
%                   L[1], n = 1
%                   max(L[1], detmaxelem(L[2..n])), n > 1

% detmaxelem(L: list, E: integer)
% L: list
% E: integer
% detmaxelem(i, o), detmaxelem(i, i) - deterministic
detmaxelem([], -1).
detmaxelem([H|T], H):-
    detmaxelem(T, H1),
    H >= H1,!.
detmaxelem([H|T], H1) :- 
    detmaxelem(T, H1),
    H1 > H.



% Model matematic
% detposmax(L, E, I) = { [], n = 0
%                       [I] U detposmax(L[2..n],E,I+1), L[1] = E
%                      detposmax(L[2..n],E,I+1), L[1] != E


% detposmax(L: list, E: integer, I: integer, R: list)
% L: list
% E: integer
% I: integer
% R: list
% detposmax(i, i, i, o), detposmax(i, i, i, i) - deterministic
detposmax([], _, _, []).
detposmax([H|T], E, I, [I|R]):-
    H =:= E,
    I1 is I + 1,!,
    detposmax(T, E, I1, R).
detposmax([H|T], E, I, R):-
    H =\= E,
    I1 is I + 1,!,
    detposmax(T, E, I1, R).


% Model matematic
% detposmaxW(L) = detposmax(L, detmaxelem(L), 1, R)


%  detposmaxW(L:list, R:list)
%  L: list
%  R: list 
%  detposmaxW(i, o), detposmaxW(i, i) - deterministic
detposmaxW([], []).
detposmaxW(L, R):- 
    detmaxelem(L, E),!,
    detposmax(L, E, 1, R).

% ------------------------------
% Model matematic
% detposmaxlist(L) = { [], n = 0
%                     detposmaxW(L[1]) U detposmaxlist(L[2..n]), L[1] este lista
%                     L[1] U detposmaxlist(L[2..n]), L[1] nu este lista

% detposmaxlist(L: list, R: list)
% L: list
% R: list
% detposmaxlist(i, o), detposmaxlist(i, i) - deterministic
detposmaxlist([], []).
detposmaxlist([H|T], [R1|R]):-
    is_list(H),!,
    detposmaxlist(T, R),
    detposmaxW(H, R1).

detposmaxlist([H|T], [H|R]):-
    number(H),!,
    detposmaxlist(T, R).

