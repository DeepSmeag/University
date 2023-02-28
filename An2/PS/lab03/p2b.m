function retval = p2b ()
  
  clf; grid on; hold on;
  v = binornd(5,1/3,1,8000);
  h = hist(v, 0:5);
  pSim = h(3)/8000
  bar(0:5, h/8000,'hist', 'faceColor', 'b');
  bar(0:5, binopdf(0:5, 5, 1/3), 'faceColor', 'y');
  set(findobj('type','patch'),'facealpha',0.7); 
  
endfunction
