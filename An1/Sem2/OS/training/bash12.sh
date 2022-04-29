#!/bin/bash
if [ $# -eq 0 ];then
    echo "Trebuie parametru un director"
    exit 1
fi
if [ ! -d $1 ];then
    echo "Trebuie sa fie un director"
    exit 1
fi
find "$1" -type f | awk -F/ '{print $NF}' | sort | uniq -c

