function printers(n)
    x1 = rndexp(n, 1/12); % 1/lambda
    x2 = exprnd(12, 1, n); % lambda
    printf('Mean of x1 %f and x2 %f\n', mean(x1), mean(x2))
    printf('Std dev of x1 %f and x2 %f\n', std(x1), std(x2))
endfunction