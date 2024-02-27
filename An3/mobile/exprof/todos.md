# Network status
- should be good enough to get the code that listens for changes to the network in; don't see any specific problem here

# Authenticate users
- integrate the new files from the server with the old version
- test old functionality with new files in place
- client: after receiving the green light for the login, [create a file and store the token in it / watch video to see if teacher did this or used preferences]; on client startup, check if [file / preferences] exists and contains token; try to auto login with token if it does
- add logout button; it triggers request to server and also deletes the token information [file or preferences]
- while user is logged in, all requests to server should contain the token; design a way to add it without much hassle

# Link resources
- saw example integrated, just have to adapt it to my needs
- each resource has unique id (unique throughout the whole system, not just per user otherwise they clash); each resource is linked to the user id; this way I can verify they belong in the correct group
- whenever responses are sent to the client, I only send to the correct client

# Online/offline
- easiest way to modify behaviour is to add if statements; maybe there is a more scalable way? like a decorator or something? or handle the request in the lower-level functions and this way I only have to write the ifs in one place, not for every potential request type
- how to store locally - files associated with the user id is an idea, but better to check video from teacher
- dialogue to inform user about later sync
- add attempt to send things when connection listener detects connection

# Pagination
- Should be easy to implement, just have to add a limit to the query and a way to get the next page

# Search and filter
- search with 1s delay, like on the website; filter buttons for 2 fields - author, title, only one of them can be selected at a time? I would say having them both on is like having none on