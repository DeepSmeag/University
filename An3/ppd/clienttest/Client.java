import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8081);
            System.out.println("Client: Connected to server");

            // Receive the sum from the server
            receiveMessage(socket);
            receiveMessage(socket);

            // Close the client socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveMessage(Socket socket) throws IOException {
        int bytesRead;
        int current = 0;
        int filecount = 0;
        InputStream in;
        String string = "";
        try {
            in = socket.getInputStream();
            DataInputStream clientData = new DataInputStream(in);

            String fileName = clientData.readUTF();
            // will throw an EOFException when the end of file is reached. Exit loop then.
            System.out.println("Cient: " + fileName);
            OutputStream output = new FileOutputStream("test.txt", false);
            long size = clientData.readLong();
            System.out.println("Cient: expecting " + size);
            byte[] buffer = new byte[1024];

            while (size > 0
                    && (bytesRead = clientData.read(buffer, 0,
                            (int) Math.min(buffer.length, size))) != -1) {
                output.write(buffer, 0, bytesRead);
                string += new String(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.close();
            System.out.println(string);

        } catch (EOFException e) {
            // means we have read all the files
            e.printStackTrace();
        }
    }

    private static void receiveMessage2(Socket socket) throws IOException {

        InputStream inputStream = socket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        // Receive the string from the server
        String message = in.readLine();
        System.out.println("Client: Received message: " + message);

        // Receive the number of bytes from the server
        int numberOfBytes = Integer.parseInt(in.readLine());
        System.out.println("Client: Expecting " + numberOfBytes + " bytes.");
        char[] messageByte = new char[1000];
        boolean end = false;
        String dataString = "";

        while (!end) {
            int bytesRead = in.read(messageByte);
            dataString += new String(messageByte, 0, bytesRead);
            if (dataString.length() == numberOfBytes) {
                end = true;
            }
            System.out.print(messageByte);
        }
        System.out.println(); // New line for clarity
        System.out.println("Client: Received " + dataString.length() + " bytes.");

    }
}
