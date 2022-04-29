#!/bin/bash
while [ $# -gt 0 ]; do
    arg=$1
    if [ -f $arg ]; then
        echo "$arg este fisier"
    elif [ -d $arg ]; then
        echo "$arg este director"
    elif [[ $arg =~ ^[0-9]+$ ]]; then
        echo "$arg este numar"
    else
        echo "$arg este altceva"
    fi
    shift
done
