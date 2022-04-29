#!/bin/bash
if [ $# -lt 1 ];then
    echo "trebuie parametru"
    exit 1
fi
if [ -d $1 ]; then
    sum=0
    for file in $(find $1 -maxdepth 1); do
        if [ -f $file ];then
            fsize=$(du -b $file | awk '{print $1}')
            sum=$((sum+fsize))
            echo "$file este fisier"
        fi
    done
    echo "Suma este $sum"
fi
