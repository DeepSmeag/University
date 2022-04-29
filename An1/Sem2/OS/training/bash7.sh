#!/bin/bash
file=""
while [ "$file" != "stop" ];do
    if [ -f $file ] && [ "$file" != "" ]; then
        if [[ $(file $file) =~ text ]];then
            echo "$file : $(head -n 1 $file | wc -w)"
        fi
    fi
    read -p "Word: " file
done

