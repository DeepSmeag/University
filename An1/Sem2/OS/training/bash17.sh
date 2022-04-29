#!/bin/bash
destination="./results"
if [ ! -d $destination ];then
    if [ ! -e $destination ];then
        mkdir $destination
    else
        echo "Destinatia exista dar nu e director"
        exit 1
    fi
fi
users=$(awk -F: '{print $1}' /etc/passwd)
for user in $users;do
    echo $user
    ips=$(last $user | head -n -2 | awk '{print $3}' | sort | uniq)
    if [ -n "$ips" ];then
        echo "$ips" > $destination/$user
    fi
done

