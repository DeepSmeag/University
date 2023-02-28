% # subm([], []).
% # subm([H|T], R):-
% #     subm(T, R).

% # subm([H|T], [H|R]):-
% #     subm(T, R).

% # % subm([1,2,3], R).

% # candidat([H|_], H).
% # candidat([_|T], R):-
% #     candidat(T, R).

% # % vai(L, Col, Flag, Rez)
% # vai(_, Col, 0 , Col).
% # vai(L, [H | Col], 0 , Rez):-
% #     candidat(L, E),
% #     not(candidat([H | Col], E)),
% #     E > H,
% #     vai(L, [E,H|Col], 0, Rez).
% # vai(L, [H|Col], 1, Rez):-
% #     candidat(L, E),
% #     E < H,
% #     vai(L, [E,H|Col], 1, Rez).

% # vai(L, [H| Col], 1, Rez):-
% #     candidat(L, E),
% #     not(candidat([H | Col], E)),
% #     E > H,
% #     vai(L, [E,H|Col], 0 , Rez).

% # wrapper(L,Rez):-
% #     candidat(L, E1),
% #     candidat(L, E2),
% #     E1 < E2,
% #     vai(L, [E1, E2], 1, Rez).


% # given list of numbers, generate all arrangements of k numbers with sum equal to a given number
% # use findall

arrangements(L, N, Sum, Rez):-
    findall(R, arrs(L, N, R), Rez).

candidat([E|_], E).
candidat([_|T], R):-
    candidat(T, R).

arrs(L, N, R):-
    candidat(L, E),
    arrs2(L, 1, N, [E], R).

arrs2(_, N, N, R, R).
arrs2(L, K, N, [H|Col], R):-
    candidat(L, H),
    not(candidat(Col, H)),
    K1 is K + 1,
    arrs2(L, K1, N, [H|Col], R).


