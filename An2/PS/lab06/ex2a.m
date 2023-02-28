function ex2a(n = 1000)
    g = @(x) exp( - x .^2), a= -2, b=2, m=1
    % g = @(x) abs(sin(exp(x))), a=-1, b=3, m=1
    % g = @(x) x.^2 ./ (1+x.^2) .* (x<=0) + sqrt(2*x - x.^2) .* (x>0), a=-1, b=2, m=1
    x = unifrnd(a, b, 1, n);
    y = unifrnd(0, m, 1, n);

    
    %  (x, g(x)) - graficul functiei
    figure(1)
    hold on;
    plot(x(y < g(x)), y(y < g(x)), 'r*', 'MarkerSize', 4);
    plot(x(y >= g(x)), y(y >= g(x)), 'b*', 'MarkerSize', 4);

    t = linspace(a, b, n);
    plot(t, g(t), '-k', 'LineWidth', 2);

    figure(2)
    hold on;
    plot(x(y < g(x)), y(y < g(x)), 'r*');
    plot(x(y >= g(x)), y(y >= g(x)), 'b*');
    t = linspace(a, b, n);
    plot(t, g(t), '-k', 'LineWidth', 2);
endfunction