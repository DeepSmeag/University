function ans = ex()
    % Gen 1000 nr cu binom
    X = binornd(10, 0.4, 1, 1000);
    % hist frecv absolute
    hist(X);
    % prob teoretica ca nr generate sa fie mai mici decat 7
    p = binocdf(7, 10, 0.4);
    printf("Probabilitatea ca un nr sa fie mai mic decat 7 este %f\n", p);
    % estimareprob ca nr generate sa fie >=5 <=9
    est = sum(X >= 5 & X <= 9) / 1000;
    printf("Estimarea probabilitatii ca un nr sa fie >=5 si <=9 este %f\n", est);

    % estimare val medie (mean) si abaterea std (std dev) pt nr generate
    printf("Estimarea valorii medii este %f\n", mean(X));
    printf("Estimarea abaterii standard este %f\n", std(X));
endfunction