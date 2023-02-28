function retval = p3 ()
    
    clf; hold on; grid on;
    
    sim = 1000;
    t = randi(6, 4, sim);
    sumPos = 4:24;
    sumSim = sum(t);
    frecvAbsol = hist(sumSim, sumPos);
    #[sumPos, frecvAbsol]'
    
   
    bar(sumPos, frecvAbsol./sim,'hist', 'FaceColor', 'b');
    
    maxFa = max(frecvAbsol);
    mostFreqSum = sumPos(frecvAbsol == maxFa);
    
    
    sumTeo=[];
    for i1 = 1:6
      for i2 = 1:6
        for i3 = 1:6
          for i4 = 1:6
            sumTeo = [sumTeo, i1+i2+i3+i4];
          endfor
        endfor
      endfor
    endfor
    
    frecvAbsolTeo = hist(sumTeo, sumPos);
    bar(sumPos, frecvAbsolTeo./sim, 'FaceColor', 'y');
    set(findobj('type','patch'),'facealpha',0.6);
    
    
    pA = sum(sumSim <= 20);
    pAB = sum(sumSim >=10 & sumSim <=20);
    
    pAT = sum(sumTeo <= 20);
    pABT = sum(sumTeo >=10 & sumTeo <=20); 
    
    legend( num2str(pAB/pA, 4) , num2str(pABT/pAT, 4) );
    
    
    
    
endfunction