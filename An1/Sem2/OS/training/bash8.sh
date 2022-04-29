#!/bin/bash
while [ $# -gt 1 ]; do
    if [ -f "$1" ];then
        count=$(grep -E -o "\<$2\>" "$1" | wc -l)
        if [ $count -ge 3 ];then
            echo "Word $2 appears $count times in $1"
        fi
    fi
    shift 2
done

if [ $# -eq 1 ];then
    echo "Final pair incomplete"
fi


