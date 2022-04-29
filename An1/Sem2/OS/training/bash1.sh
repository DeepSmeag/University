#!/bin/bash
if [ $# -lt 1 ]; then
    echo "insuficiente argumente"
else

    find $1 -type f | grep -E -c '\.c$'
fi
