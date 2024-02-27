# Network status
- should be good enough to get the code that listens for changes to the network in; don't see any specific problem here
- update: there were some problems, capacitor/network does not actually check for connection on web, so I had to discover a way to stop the connection from devtools/network; offline / no throttling, that works
- from what I read on native it works well
- but it's done xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

# Authenticate users
- integrate the new files from the server with the old version
- test old functionality with new files in place
- client: after receiving the green light for the login, [create a file and store the token in it / watch video to see if teacher did this or used preferences]; on client startup, check if [file / preferences] exists and contains token; try to auto login with token if it does
- ~add logout button; it triggers request to server and also deletes the token information [file or preferences]~ xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
- while user is logged in, all requests to server should contain the token; design a way to add it without much hassle
- for server: when user logs in, generate token and memorize it in db; when user logs out, remove that token; all verifications for the user should be made on the token, not the username xxxxxxxxxxxxxxxxxxxxxxxx

# Link resources
- saw example integrated, just have to adapt it to my needs
- each resource has unique id (unique throughout the whole system, not just per user otherwise they clash); each resource is linked to the user id; this way I can verify they belong in the correct group
- whenever responses are sent to the client, I only send to the correct client xxxxxxxxxxxxxxxxxxxxxxxxxx

# Online/offline
- easiest way to modify behaviour is to add if statements; maybe there is a more scalable way? like a decorator or something? or handle the request in the lower-level functions and this way I only have to write the ifs in one place, not for every potential request type
- how to store locally - files associated with the user id is an idea, but better to check video from teacher
- dialogue to inform user about later sync ; IonAlert?
- add attempt to send things when connection listener detects connection
- use syncApp; networkState triggers it so I know to check the list of calls that didn't go through and execute them one by one;
when Internet's off, I know from the ItemEdit I got a simple check and store them in a list in localStorage xxxxxxxxxxxxxxxxxxxxxxxxx

# Pagination
- Should be easy to implement, just have to add a limit to the query and a way to get the next page xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

# Search and filter
- search with 1s delay, like on the website; filter buttons for 2 fields - author, title, only one of them can be selected at a time? I would say having them both on is like having none on
- pain in the ass with synchronizing everything; I probably did things in a way they shouldn't be done...but anyway
- to filter stuff I need to change the objects; let them have 2 text fields, and then when I search I search based on the selected field at that time