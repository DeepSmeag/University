#!/bin/bash
libs=""
regex="\<C\>"
while [ $# -gt 0 ];do
    if [[ $(file $1) =~ $regex ]];then
        grep -E -o '^#include <(.*)>' $1
        #libs="$libs$greps\n"
    fi
    shift
done > out.txt
#libs2=${libs%??}
#echo -e $libs2
