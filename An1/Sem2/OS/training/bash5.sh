#!/bin/bash
for arg in $@; do
    if [ -f $arg ]; then
        du -b $arg
    fi
done | sort -n
