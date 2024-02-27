#!/bin/bash

run_program() {
    program="$1"
    runs="$2"
    rows="$3"
    columns="$4"
    threads="$5"

    total_output=0

    # Run the program the specified number of times and accumulate the output
    for ((i=1; i<=$runs; i++)); do
        # Run the program with the provided arguments
        output=$(./$program $rows $columns $threads)
        
        # Check if the program's exit status is not 0 (indicating an error)
        if [ $? -ne 0 ]; then
            echo "Error running $program"
            exit 1
        fi
        
        total_output=$(awk "BEGIN {print $total_output + $output; exit}")
    done

    # Calculate the average
    average_output=$(awk "BEGIN {print $total_output / $runs; exit}")

    # echo "Total output for $runs runs with $rows rows, $columns cols and $threads threads: $total_output ms"
    echo "Average output for $runs runs with $rows rows, $columns cols and $threads threads: $average_output ms"
}

# Check if the correct number of arguments is provided
if [ "$#" -ne 0 ]; then
    # echo "Usage: $0 <program> <runs> <rows> <columns> <threads>"
    echo "Usage: $0 - the rest is automated"
    exit 1
fi

programname="cmatrix.o"
runs=10
size10=10
size1000=1000
size10000=10000
th1=1
th2=2
th4=4
th8=8
th16=16
# Call the function with the provided arguments
run_program $programname $runs $size10 $size10 $th1
run_program $programname $runs $size10 $size10 $th2
echo "------------------------------------------"
run_program $programname $runs $size1000 $size1000 $th1
run_program $programname $runs $size1000 $size1000 $th2
run_program $programname $runs $size1000 $size1000 $th4
run_program $programname $runs $size1000 $size1000 $th8
run_program $programname $runs $size1000 $size1000 $th16
echo "------------------------------------------"
run_program $programname $runs $size10000 $size10000 $th1
run_program $programname $runs $size10000 $size10000 $th2
run_program $programname $runs $size10000 $size10000 $th4
run_program $programname $runs $size10000 $size10000 $th8
run_program $programname $runs $size10000 $size10000 $th16

