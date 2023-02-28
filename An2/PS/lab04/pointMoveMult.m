function maxPos = pointMoveMult(p=0.5, k = 10, m = 10)
    pos = zeros(1, m);
    availablePos = -k:k;
    for i = 1:k
        tmp = pointMoveOnce(p, k);
        pos(i) = tmp(end);
    endfor
    h = hist(pos, availablePos);
    bar(availablePos, h, 'hist')
    maxFrq = max(h);
    h
    % maxPos = availablePos(h == maxFrq);
    maxPos = find(h==maxFrq) - k - 1
endfunction