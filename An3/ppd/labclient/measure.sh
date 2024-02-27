

runny() {
    local dx=$1
    local p_r=$2
    local p_w=$3
    local dt=$4
    

    # Record the start time
    current_time() {
    date +%s%3N
    }
    start_time=$(current_time)

    # Start the server in the background
    java Server $p_r $p_w $dt 5 &

    # Store the server's process ID for later use
    server_pid=$!

    # Start the client in the background
    # Store the client's process ID for later use
    java Client 0 $dx &
    client_pid_0=$!
    java Client 1 $dx &
    client_pid_1=$!
    java Client 2 $dx &
    client_pid_2=$!
    java Client 3 $dx &
    client_pid_3=$!
    java Client 4 $dx &
    client_pid_4=$!

    # Wait for the client process to finish
    wait $client_pid_0
    wait $client_pid_1
    wait $client_pid_2
    wait $client_pid_3
    wait $client_pid_4


    # Wait for the server process to finish
    wait $server_pid
    # Record the end time
    end_time=$(current_time)

    # Calculate the execution time
    execution_time=$((end_time - start_time))

    echo "Configuration dx: $dx, p_r: $p_r, p_w: $p_w, dt: $dt"
    echo "Server process execution time: ${execution_time} milliseconds"
    echo "----------------------------------------------"
    # Your existing script logic using dx, p_r, p_w, dt

    # Add your script logic here using the parameters
    # For example:
    # echo "Performing calculations with the provided parameters..."
}
# runny 1 4 4 1
# runny 1 4 4 2
# runny 1 4 4 4

# runny 1 2 2 1
# runny 1 2 2 2
# runny 1 2 2 4

# runny 1 4 2 1
# runny 1 4 2 2
# runny 1 4 2 4

# runny 1 4 8 1
# runny 1 4 8 2
# runny 1 4 8 4

# runny 2 4 4 1
# runny 2 4 4 2
# runny 2 4 4 4

# runny 2 2 2 1
# runny 2 2 2 2
# runny 2 2 2 4

# runny 2 4 2 1
# runny 2 4 2 2
# runny 2 4 2 4

# runny 2 4 8 1
# runny 2 4 8 2
runny 2 4 8 4
# Δx=1, 2 sec
# Testare:
# A)
# p_r = 4
# p_w=4
# Δt = 1ms, 2ms, 4ms
# B)
# p_r = 2
# p_w=2
# Δt = 1ms, 2ms, 4ms
# C)
# p_r = 4
# p_w=2
# Δt = 1ms, 2ms, 4ms
# D)
# p_r = 4
# p_w=8
# Δt = 1ms, 2ms, 4ms