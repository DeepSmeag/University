function darts(n = 0)
    clf;   % clear figure and hold on
    t = linspace(0, 2 * pi, 360);
    polar(t, 4 * ones(1,360), 'w');
    hold on;
    [x,y] = boxmuller(n);
    plot(x,y, 'r*');
    title('Box-Muller vs Normrnd');
    axis([-4 4 -4 4]);

    m = mean(sqrt(x.^2 + y.^2) < 0.5);
    fprintf('Mean: %f\n', m);

    i = exp(-1/8); %frecv relativa teoretica
    z = normrnd(0,i,2,n);
    plot(z(1,:),z(2,:),'c*');
    polar(t, 0.5 * ones(1,360), 'k');
    m2 = mean(sqrt(z(1,:).^2 + z(2,:).^2) < 0.5);
    fprintf('Mean2: %f\n', m2);

endfunction
