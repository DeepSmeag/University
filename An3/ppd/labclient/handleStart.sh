#!/bin/bash

# Record the start time
current_time() {
  date +%s%3N
}
start_time=$(current_time)

# Start the server in the background
java Server 4 4 1 5 &

# Store the server's process ID for later use
server_pid=$!

# Start the client in the background
# Store the client's process ID for later use
java Client 0 1 &
client_pid_0=$!
java Client 1 1 &
client_pid_1=$!
java Client 2 1 &
client_pid_2=$!
java Client 3 1 &
client_pid_3=$!
java Client 4 1 &
client_pid_4=$!

# echo "Server process ID: ${server_pid}"
# echo "Client0 process ID: ${client_pid_0}"
# echo "Client1 process ID: ${client_pid_1}"
# echo "Client2 process ID: ${client_pid_2}"
# echo "Client3 process ID: ${client_pid_3}"
# echo "Client4 process ID: ${client_pid_4}"


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

echo "Server process execution time: ${execution_time} milliseconds"


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