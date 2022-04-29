#!/bin/bash
for file in $(ls -l | awk '{print $1","$NF}');do
    if [[  "$file" =~ ^..w..w..w. ]];then
        echo $file
        chmod a-w $(awk -F, '{print $2}' <<< "$file")
    fi
done
