function ex3(n= 1000)
    I = rand(1,n)
    T = exprnd(5, 1, n) .* (I<=0.4)  + unifrnd(4, 6, 1, n) .* (I>0.4);


    % a)
    [mean(T), std(T)]
    % b)

    mean(T>5)

    % c)
    % T>5 & I > 0.4
    % sum((T>5) & (I > 0.4)) / sum(T>5)
    countPandT = 0; countT = 0;
    for i = 1:n
        if T(i) > 5
            countT = countT + 1;
            if I(i) > 0.4
                countPandT = countPandT + 1;
            end
        end
    end

    countPandT / countT

endfunction