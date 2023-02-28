function p3(arr,k)
    % unique(perms(arr))(:)
    unique(unique(perms(arr),"rows")(end:-1:1,1:k), "rows")
endfunction