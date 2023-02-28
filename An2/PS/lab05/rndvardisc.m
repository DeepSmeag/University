function x = rndvardisc(X, P , n)
% RNDVARDISC Random number generation for discrete random variables.
% X = RNDVARDISC(X, P, N) generates N random numbers from the discrete
% random variable X with probability mass function P.
% X = vect val pos
% P = vect prob coresp X
% N = numar de valori generate
    q = cumsum(P); % [p1, p1+p2, p1+p2+p3, ...]
    x = zeros(1, n);
    for i = 1:n
        u = rand;
        j = 1;
        while u > q(j)
            j = j + 1;
        endwhile
        x(i) = X(j);
    endfor
