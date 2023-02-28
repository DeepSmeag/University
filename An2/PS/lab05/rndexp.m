function X = rndexp(n, lambda)
    X = -log(1-rand(1,n)) / lambda;
endfunction
