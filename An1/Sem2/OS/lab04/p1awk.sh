awk -F: '/[aeiou]$/{count1++} /[bcdfghkjlmnpqrstvwxyz]$/{count2++} END{print count1 " " count2}' /etc/passwd
