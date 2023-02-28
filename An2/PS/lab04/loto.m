function prob = loto(m=1000)
    p = hygepdf(3:6, 49, 6, 6);
    % params:
    %  3:6 - number of balls we want to draw from our marked balls
    %  49 - total number of balls
    %  6 - number of marked balls
    %  6 - number of balls drawn
    p = sum(p);
    x = geornd(p,1,m); % pana aici e pct a)
    prob_est = mean(x>=10); % pct b)
    prob = prob_est;
    prob_teoretica = 1 - sum(geopdf(0:9, p))
endfunction