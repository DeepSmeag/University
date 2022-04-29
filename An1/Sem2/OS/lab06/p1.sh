#!/bin/bash
if  [ $# -gt 0 ];then
    find $1 -maxdepth 1 -type f -exec du -bs {} + | awk 'BEGIN{sum=0;} system("test -f $2")==0 {sum+=$1} END{print sum}'
    
fi

