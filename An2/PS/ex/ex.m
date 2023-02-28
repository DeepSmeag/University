function ans = ex()
    % email is sent to 4 servers with probability i/10, where i is the number of the server
    % each server processes the email in exponential distribution with mean i, where i is the number of the server
    
    % calculate the probability to process an email in less than 3 seconds
    % for each server
    % and sum them up
    ans = sum(expcdf(3, 1:4) .* (1:4) / 10);
    % calculate the mean time to process an email
    % for each server
    % and sum them up
    p1 = exprnd(1, 1, 1000000);
    p2 = exprnd(2, 1, 1000000);
    p3 = exprnd(3, 1, 1000000);
    p4 = exprnd(4, 1, 1000000);
    p1 = mean(1);
    p2 = mean(2);
    p3 = mean(3);
    p4 = mean(4);
    ans = 0;
    ans = ans + p1 * 0.1;
    ans = ans + p2 * 0.2;
    ans = ans + p3 * 0.3;
    ans = ans + p4 * 0.4;
    % estimate the probability of processing an email in more than 3 seconds
    % by generating numbers
    % and counting the number of numbers greater than 3
    % and dividing by the total number of numbers
    % and multiplying by 100
    p1 = exprnd(1, 1, 1000000);
    p2 = exprnd(2, 1, 1000000);
    p3 = exprnd(3, 1, 1000000);
    p4 = exprnd(4, 1, 1000000);
    p1 = sum(p1 > 3) / 1000000;
    p2 = sum(p2 > 3) / 1000000;
    p3 = sum(p3 > 3) / 1000000;
    p4 = sum(p4 > 3) / 1000000;
    ans = 0;
    ans = ans + p1 * 0.1;
    ans = ans + p2 * 0.2;
    ans = ans + p3 * 0.3;
    ans = ans + p4 * 0.4;

    
    

endfunction