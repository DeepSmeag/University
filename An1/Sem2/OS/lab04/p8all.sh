#!/bin/bash
ls -l | awk '{print $1,$NF}' | grep -E --color '[-rwx]+ ' | sed -E 's/.([-rwx]{3})(.*) /\1 /'
