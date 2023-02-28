function ex1(m=165, sigma=10, n=2000)
    close all;

    x =  normrnd(m, sigma, 1, n);
    % i)
    figure(1)
    hist(x); % impartirea se face automat in 10 intervale
    % ii)
    figure(2)
    [~, centers] = hist(x);

    hist(x, centers, 10 / (max(x) - min(x)));

    hold on;
    t = linspace(min(x), max(x), n);
    plot(t, n * normpdf(t, m, sigma), 'LineWidth', 2, 'Color', 'r');
    % iii)
    [mean(x), m]
    [std(x), sigma]
    % rezultat din simulari
    countP = 0;
    for i = 1 : n
        if (x(i)>=160) && (x(i)<=170)
            countP++;
        endif
    endfor
    countP / n
    proportie_simulari = mean((x>=160) & (x<=170))
    proportie_teoretica = normcdf(170, m, sigma) - normcdf(160, m, sigma)

endfunction