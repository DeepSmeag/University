sed -E 's/([aeiou]{3,})/(\1)/gi' /etc/passwd | grep -E "\([aeiou]{3,}\)" --color
