#!/bin/bash

dir=${1:-"."}

if [ -d $dir ];then
    for f in $(find $dir -type f);do
        if file $f | grep -E -q "text"; then
            size=$(du -b $f | awk '{print $1}')
            perms=$(ls -l $f | awk '{print $1}')
            lines=$(sort $f | uniq | wc -l)
            echo $size" - "$perms" - "$lines
        fi
    done
fi
