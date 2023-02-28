function retval = p1 ()

  urn = 'rrrrrbbbgg';
  sim = 5000;
  pA = 0;
  pAB = 0;
  pCond = 0;
  
  for i = 1:sim
    ext = randsample(urn, 3);
    
    if any(ext == 'r') && all(ext == 'r')
      pCond++;
    endif
    
    if any(ext == 'r')
      pA++;
    endif
    
    if all(ext == 'r') 
      pAB++;
    endif
    
  endfor
  pA/sim
  pAB/pA
  pCond/sim

endfunction
