#!/bin/bash

# Define the function
run_mpi_command() {
    local p=$1
    local total_time=0

    for ((i=1; i<=10; i++)); do
        # Run the mpirun command and capture the output
        output=$(mpirun --oversubscribe -c $p main)
        
        # Extract the time from the output (assuming format is "488.354 ms")
        time=$(echo "$output" | awk '/[0-9]+\.[0-9]+ ms/ {print $1}')

        # If the time is not empty, add it to the total
        if [ -n "$time" ]; then
            total_time=$(awk "BEGIN {print $total_time + $time}")
        else
            echo "Failed to extract time from mpirun output."
        fi
    done

    # Calculate the average time
    average_time=$(awk "BEGIN {print $total_time / 10}")

    echo "Average Elapsed Time for $p processes: $average_time ms"
}

# Example usage: run the function with different numbers of processes
run_mpi_command 5
run_mpi_command 9
run_mpi_command 21
