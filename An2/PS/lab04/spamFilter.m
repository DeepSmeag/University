function decision = spamFilter(fileHam, fileSpam, email1, email2)
    hamW = strsplit(fileread(fileHam), ' ');
    hamUW = unique(hamW);
    hamUW = hamUW(2:end);

    spamW = strsplit(fileread(fileSpam), ' ');
    spamUW = unique(spamW);
    spamUW = spamUW(2:end);

    hamL = length(hamW);
    spamL = length(spamW);

    hamUL = length(hamUW);
    spamUL = length(spamUW);
    % Calculate each word's probability of being in a spam or ham email
    hamP = zeros(1, hamUL);
    for i = 1: hamUL
        counter=0;
        for j = 1: hamL
            if strcmp(hamUW(i), hamW(j))
                counter = counter + 1;
            end
        end
        hamP(i) = counter/hamL;
    end
    % Do the same for spam
    spamP = zeros(1, spamUL);
    for i = 1: spamUL
        counter=0;
        for j = 1: spamL
            if strcmp(spamUW(i), spamW(j))
                counter = counter + 1;
            end
        end
        spamP(i) = counter/spamL;
    end

    % Apply probabilities to the test email1
    email1W = strsplit(fileread(email1), ' ');
    email1L = length(email1W);
    email1Pham = zeros(1, email1L);
    email1Pspam = zeros(1, email1L);
    for i=1:hamUL
        counter = 0;
        for j = 1:email1L
            if strcmp(hamUW(i), email1W(j))
                counter = counter + 1;
            end
        end
        if counter == 0
            email1Pham(i) = 1 - hamP(i);
        else
            email1Pham(i) = hamP(i);
        end
    end
    for i=1:spamUL
        counter = 0;
        for j = 1:email1L
            if strcmp(spamUW(i), email1W(j))
                counter = counter + 1;
            end
        end
        if counter == 0
            email1Pspam(i) = 1 - spamP(i);
        else
            email1Pspam(i) = spamP(i);
        end
    end
    % Do the same for email2
    email2W = strsplit(fileread(email2), ' ');
    email2L = length(email2W);
    email2Pham = zeros(1, email2L);
    email2Pspam = zeros(1, email2L);
    for i=1:hamUL
        counter = 0;
        for j = 1:email2L
            if strcmp(hamUW(i), email2W(j))
                counter = counter + 1;
            end
        end
        if counter == 0
            email2Pham(i) = 1 - hamP(i);
        else
            email2Pham(i) = hamP(i);
        end
    end
    for i=1:spamUL
        counter = 0;
        for j = 1:email2L
            if strcmp(spamUW(i), email2W(j))
                counter = counter + 1;
            end
        end
        if counter == 0
            email2Pspam(i) = 1 - spamP(i);
        else
            email2Pspam(i) = spamP(i);
        end
    end
    % decision = [
    %     [],
    %     []
    %     ];
    hamPemails = [prod(email1Pham) * hamL / (spamL + hamL), prod(email2Pham) * hamL / (spamL + hamL)];
    spamPemails = [(prod(email1Pspam) * spamL / (spamL + hamL)), (prod(email2Pspam) * spamL / (spamL + hamL))];
    decision = hamPemails> spamPemails;
    disp("0 = spam, 1 = ham");
endfunction

