#!/bin/bash

# Function to run ./secv and extract the float number
run_secv() {
    result=$(./secv)
    echo "$result" | grep -oP '\d+(\.\d+)?'  # Extract float number using grep
}

# Number of times to run ./secv
num_runs=10

# Initialize sum to calculate average
sum=0

# Run ./secv multiple times and calculate the sum
for ((i = 1; i <= num_runs; i++)); do
    # echo "Running ./secv for the $i time"
    float_number=$(run_secv)
    # echo "Float number: $float_number"
    
    # Add the float number to the sum
    sum=$(awk "BEGIN {print $sum + $float_number}")
done

# Calculate the average
average=$(awk "BEGIN {print $sum / $num_runs}")

echo "Average: $average"
