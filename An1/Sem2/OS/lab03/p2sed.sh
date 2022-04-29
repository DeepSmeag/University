sed 's/[a-z]/\U&/g' /etc/passwd | grep -E "[A-Z]" --color
sed -E "y/[a-z]/[A-Z]/" /etc/passwd | grep -E "[A-Z]" --color
sed -E "s/([a-z])/\U\1/g" /etc/passwd 
