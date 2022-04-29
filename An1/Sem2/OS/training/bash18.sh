#!/bin/bash
users="-e"
if [ $# -gt 0 ];then
    users=""

    while [ $# -gt 0 ];do
        users="$users -u $1"
        shift
    done
fi
while true;do
    clear
    ps -f $users | tail -n +2 | awk '{print $1}' | sort | uniq -c | sort -r
    sleep 1
done
