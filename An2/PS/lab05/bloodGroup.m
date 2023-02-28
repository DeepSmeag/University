function bloodGroup(n)
    X = [0.46, 0.4, 0.1, 0.04];
    %      0     A    B   AB

    % generate n pseudo-random numbers for the given random discrete variable X
    x = rndvardisc([0,1,2,3],X,n);
    freq = hist(x,0:3)
    freq;

    % now use randsample 
    x = randsample(0:3,n,true,X);
    freq2 = hist(x,0:3);

    % compare the 2 histograms
    figure
    subplot(2,1,1)
    title('histogram of rndvardisc')
    bar(0:3,freq/n)
    subplot(2,1,2)
    title('histogram of randsample')
    bar(0:3,freq2/n)
    freq = freq/n
    freq2 = freq2/n
    printf("Groups in rndvardisc vs randsample\n")
    printf("Group O: %3f vs %3f vs reference: 0.46\n", freq(1), freq2(1))
    printf("Group A: %3f vs %3f vs reference: 0.4\n", freq(2), freq2(2))
    printf("Group B: %3f vs %3f vs reference: 0.1\n", freq(3), freq2(3))
    printf("Group AB: %3f vs %3f vs reference: 0.04\n", freq(4), freq2(4))


endfunction