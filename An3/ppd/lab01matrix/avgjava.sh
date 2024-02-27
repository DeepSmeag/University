#!/bin/bash

# Define the number of runs
runs=10

# Initialize total sum
total=0.0

# Compile the Java program
javac Main.java

# Loop to run the program 10 times
for ((i=1; i<=runs; i++)); do
    # Run the Java program and capture its output
    output=$(java Main)

    # Use 'bc' to perform floating-point addition
    total=$(echo "$total + $output" | bc)
done

# Calculate the average
average=$(echo "scale=2; $total / $runs" | bc)

echo "Total: $total"
echo "Average: $average"