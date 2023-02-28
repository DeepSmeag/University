function chance=p1(numTries)
    occurences = 0;
    for numTry=1:numTries
        group = randi(365,1,23);
        isFavorable = (length(unique(group)) - length(group));
        if (isFavorable != 0)
            occurences++;
        endif
    endfor
    chance = occurences/numTries;
endfunction
