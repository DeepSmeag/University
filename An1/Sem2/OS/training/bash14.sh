#!/bin/bash
if [ $# -lt 1 ]; then
    echo "Trebuie parametru director"
    exit 1
fi
if [ ! -d $1 ];then
    echo"Nu e director"
    exit 1
fi
for file in $(find $1);do
    #if $(echo $file | grep -E "^.*.c$");then
    if [ -f "$file" && $(grep -E "^.*.c$" "$file") ];then
        echo $file" a fost sters"
        rm $file
    fi
done
find $1 -type f | sort
