#!/bin/bash
for item in $(find -type l);do
    if [ -e $item ];then
        echo "$item este link valid"
    else
        echo "$item nu este link valid"
    fi
done
