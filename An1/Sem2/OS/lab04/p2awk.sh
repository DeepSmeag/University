awk -F: ' (NF%2==1){print NR " " $(int(NF/2)+1)}' /etc/passwd
