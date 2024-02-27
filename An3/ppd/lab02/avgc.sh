#!/bin/bash

# Check if a program is not provided as an argument
if [ "$#" -ne 0 ]; then
    echo "Usage: $0 <program>"
    exit 1
fi

program="cmatrix.o"
total_output=0

# Run the program 10 times and accumulate the output
for ((i=1; i<=10; i++)); do
    # run the program with 10 10 1 as arguments
    output=$(./$program 10 10 1)
    # Check if the program's exit status is not 0 (indicating an error)
    if [ $? -ne 0 ]; then
        echo "Error running $program"
        exit 1
    fi
    total_output=$(awk "BEGIN {print $total_output + $output; exit}")
done

# Calculate the average
average_output=$(awk "BEGIN {print $total_output / 10; exit}")

echo "Total output: $total_output ms"
echo "Average output: $average_output ms"