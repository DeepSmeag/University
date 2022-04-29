#!/bin/bash
string=""
for user in $(ps -ef | tail -n +2 | sort | awk '{print $1}' | uniq);do
    string="$user@scs.ubbcluj.ro,$string"
done
echo ${string%?}
#echo $string
