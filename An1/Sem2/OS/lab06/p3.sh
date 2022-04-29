#!/bin/bash
a="";
while [ $# -gt 0 ];
do
    a="$a-u $1 ";
    shift;
done
if [ -z "$a" ]; then
    ps -ef | sort -r | head -n -1 | awk '{print $1}' | uniq -c | awk '{print $1 $2; system("sleep 1")}'
   echo ok 
else
    ps -f $a | sort -r | head -n -1 | awk '{print $1}' | uniq -c | awk '{print $1 $2; system("sleep 1")}'
fi

