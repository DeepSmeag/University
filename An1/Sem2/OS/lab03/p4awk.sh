awk -F: '$0 ~ /gr217/ {print $5}' /etc/passwd
