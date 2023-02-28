function ans = ex2()
    % 2 servers process emails
    % the 1st server processes emails in exponential distribution with mean 4 seconds
    % the 2nd server processes emails in uniform distribution between 1 and 3 seconds
    % if the email wasn't processed in 4 seconds by the first server, it gets processed by the second server

    % calculate the probability of an email being processed in less than 3 seconds
    % code here
    ans = expcdf(3, 4);

    % estimate the mean time an email is processed
    % we have the mean of the first server and the mean of the second server multiplied by the probability of the email being processed by the second server

    s1 = exprnd(4, 1, 1000000);
    s2 = unifrnd(1, 3, 1, 1000000);
    probs1 = expcdf(4, 4);
    ans = mean(s1) * probs1 + mean(s2) * (1 - probs1);


    % estimate the probability of an email being processed in less than 4 seconds
    % we take elements < 4 from 1st server; the 2nd one does not apply
    % code here
    ans = sum(s1 < 4) / 1000000;

endfunction