function int = MC1(g,a,b,m,n)
    x = unifrnd(a,b,1,n);
    y = unifrnd(0,m,1,n);
    int = (b-a) * m * mean(y <= g(x));
endfunction
