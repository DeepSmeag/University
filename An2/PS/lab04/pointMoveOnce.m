function endPos = pointMoveOnce(p = 0.5, k =10)
    pos = zeros(1, k+1);
    % each position is randomly +1 or -1 previous based on p
    for i = 2:k+1
        pos(i) = pos(i-1) + 2* binornd(1,p) - 1;
    end
    endPos = pos;
endfunction

