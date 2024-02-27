#!/bin/bash
# Function to run ./secv and extract the float number
run_secv() {
    local p=$1
    local N=$2
    num_runs=10
    # Initialize sum to calculate average
    local sum=0
    for ((i = 1; i <= num_runs; i++)); do
        result=$(./gpar $p $N)
    
        sum=$(awk "BEGIN {print $sum + $result}")
    done
    # echo "$result" | grep -oP '\d+(\.\d+)?'  # Extract float number using grep
    average=$(awk "BEGIN {print $sum / $num_runs}")
    echo "Average Elapsed Time for $p processes with $N readers: $average ms"
}
run_secv 6 4
run_secv 8 4
run_secv 16 4

