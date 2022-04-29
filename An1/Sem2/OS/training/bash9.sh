#!/bin/bash
while [ $# -gt 0 ]; do
    if [ -f "$1" ];then
        du -b $1
    fi
    shift
done | sort -nr
