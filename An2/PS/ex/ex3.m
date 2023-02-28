function ans = ex3()
    % random variable X~N(100,20)
    X = normrnd(100,20,1,1000);
    
    % estimate A: 80 < X < 140
    ans = (sum(X > 80) - sum(X > 140)) / 1000;

    % calculate theoretical probability of A
    % P(80 < X < 140) = P(X < 140) - P(X < 80)
    ans = normcdf(140,100,20) - normcdf(80,100,20);

    % hist with 13 bars
    disp(hist(X, 13) ./ sum(X))
    
    
    % plot density function of X
    % x = 94:106;
    % length(x)
    % length(bins)
    % bar(x, bins);

    hold on;
    plot(1:200, normpdf(1:200,100,20) , 'r');
    legend('histogram','density function');



endfunction