#!/bin/bash
prevuser=""
sum=0
count=0
for userpid in $(ps -ef | tail -n +2 | awk '{print $1","$2}'| sort);do
    current_user=$(echo "$userpid" | awk -F, '{print $1}')
    currentpid=$(echo "$userpid" | awk -F, '{print $2}')
    if [ "$current_user" != "$prevuser" ];then
        if [ $count -gt 0 ];then
            echo "Avg $current_user is "$((sum/count))
        fi
        prevuser=$current_user
        sum=0
        count=0
    fi
    sum=$((sum + currentpid))
    count=$((count + 1))
done

