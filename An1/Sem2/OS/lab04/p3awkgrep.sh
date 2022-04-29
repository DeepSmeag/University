ps -ef | grep -E '([^ ]+[ ]+){7}(vim|joe|emacs|nano|pico)' |awk '{s+=$2}END{print s}'
