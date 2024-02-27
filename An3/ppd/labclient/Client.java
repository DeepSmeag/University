import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private List<String> filenames = new ArrayList<>();
    private int dx;
    private int countryId;
    private List<Object[]> scoresList;

    private void generateFilenames() {
        for (int i = 0; i < 10; i++) {
            this.filenames.add(String.format("RezultateC%d_P%d.txt", countryId, i + 1));
        }
    }

    private List<Object[]> readTuplesFromFiles(List<String> filenames) {
        List<Object[]> scoresList = new ArrayList<>();

        for (String filename : filenames) {
            try (BufferedReader reader = new BufferedReader(new FileReader("data/" + filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        // Assuming the format of each line is (int, int)
                        String[] parts = line.replaceAll("\\(|\\)", "").split(",");
                        int first = Integer.parseInt(parts[0].trim());
                        int second = Integer.parseInt(parts[1].trim());
                        scoresList.add(new Object[] { first, second });
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + filename);
            }
        }

        return scoresList;
    }

    public Client(int countryId, int dx) {
        this.countryId = countryId;
        this.dx = dx;
        generateFilenames();
        this.scoresList = readTuplesFromFiles(filenames);
    }

    public void sendData(Socket socket) {
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            // send the scoresList pairs to the server
            for (int i = 0; i < scoresList.size(); i++) {
                if (i % 20 == 0) {
                    // out.flush();
                    try {
                        Thread.sleep(dx * 1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int first = (int) scoresList.get(i)[0];
                int second = (int) scoresList.get(i)[1];
                writer.println(first + " " + second);
                // out.write((first + " " + second + "\n").getBytes());
                // System.out.println("Client " + countryId + " --- Sending data index" + i + ":
                // " + first + " " + second);
            }
            // out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void requestLeaderboard(Socket socket) {
        try {
            // Get output stream to send data to the server

            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            // Send the "request" message
            writer.println("request");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveLeaderboard(Socket socket) {
        // Get input stream to receive data from the server
        try {
            // System.out.println("Client " + countryId + ": waiting for leaderboard...");
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            for (int i = 0; i < 5; i++) {
                String line = reader.readLine();
                if (line != null) {
                    int score = Integer.parseInt(line);
                    // System.out
                    // .println("Client " + countryId + " --- Received leaderboard item: " + i +
                    // " - " + score);
                } else {
                    // System.out.println("Error: Unexpected end of stream.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveFile(Socket serverSocket) {
        try {
            InputStream inputStream = serverSocket.getInputStream();
            DataInputStream in = new DataInputStream(inputStream);

            String filename = in.readUTF();
            Long bytesExpected = in.readLong();
            // System.out.println("Client: receiving " + filename + " " + bytesExpected);
            // Construct the full path for the file inside the specified folder
            String folderName = "client" + countryId;
            String fullPath = folderName + File.separator + filename;
            // Create the folder if it doesn't exist
            File folder = new File(folderName);
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    // System.out.println("Client: Folder '" + folderName + "' created.");
                } else {
                    // System.out.println("Client: Failed to create folder '" + folderName + "'.");
                    return;
                }
            }
            OutputStream outputStream = new FileOutputStream(fullPath, false);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while (bytesExpected > 0
                    && (bytesRead = in.read(buffer, 0,
                            (int) Math.min(buffer.length, bytesExpected))) != -1) {
                // output.write(buffer, 0, bytesRead);
                outputStream.write(buffer, 0, bytesRead);
                bytesExpected -= bytesRead;
            }
            outputStream.close();
            // System.out.println("Client: Received file '" + filename + "' with " +
            // bytesExpected
            // + " bytes remaining.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // take from args the following arguments: countryId - int, dx - int
        int countryId = Integer.parseInt(args[0]);
        int dx = Integer.parseInt(args[1]);
        // dx = 0;
        Client client = new Client(countryId, dx);
        try {
            Socket socket = new Socket("localhost", 8082);
            // System.out.println("Client " + countryId + " connected to server. Sending
            // data...");
            client.sendData(socket);
            // System.out.println("Client " + countryId + ": requesting leaderboard...");
            client.requestLeaderboard(socket);
            client.receiveLeaderboard(socket);
            client.receiveFile(socket);
            client.receiveFile(socket);
            // Thread.sleep(1000);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
