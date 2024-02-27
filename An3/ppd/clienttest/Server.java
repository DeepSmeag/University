import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            System.out.println("Server: Waiting for connections...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Server: Accepted connection from client");

            // Send the sum back to the client
            sendMessage("Heyy", 251, clientSocket);
            sendMessage("Heyy", 231, clientSocket);

            checkFile("test.txt");

            // Close the server socket
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkFile(String fileName) {
        try {

            FileInputStream file = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            // Get the size of the file
            long fileSize = file.available();

            // Create a byte array to hold the file data
            byte[] fileBytes = new byte[(int) fileSize];

            // Read the file data into the byte array
            int bytesRead = file.read(fileBytes);
            System.out.println("Server: " + bytesRead + " bytes read from file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void sendMessage(String message, long numberOfBytes, Socket clientSocket) throws IOException {

        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        // PrintWriter out = new PrintWriter(outputStream, true);
        // Send the string as the first line
        // out.println(message);
        outputStream.writeUTF(message);
        // Send the number of bytes as the second line
        outputStream.writeLong(numberOfBytes);

        // Send the specified number of bytes
        for (int i = 0; i < numberOfBytes; i++) {
            outputStream.write('a'); // Sending a simple sequence of bytes, you can modify as needed
        }
        outputStream.flush();

    }
}
