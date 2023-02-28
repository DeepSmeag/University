function [X,Y] = boxmuller(n)
    u = rand(2,n);
    R = sqrt(-2 * log(u(1,:)));
    theta = 2 * pi * u(2,:);
    X = R .* cos(theta);
    Y = R .* sin(theta);
endfunction