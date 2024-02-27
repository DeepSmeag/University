import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    static private boolean requested[] = new boolean[5];
    static private boolean sent[] = new boolean[5];
    static private long lastLeaderboardUpdate = 0;
    static private int p_r;
    static private int p_w;
    static private int dt;
    static private int[] leaderboard = new int[5];
    static private BlockingQueue<Object[]> numberQueue = new LinkedBlockingQueue<>();
    static private ReentrantLock[] locks = new ReentrantLock[5];
    static private boolean doneReading = false;
    static private ArrayList<Score> scores = new ArrayList<>();
    static private ReentrantLock scoreLock = new ReentrantLock();
    static private ReentrantLock queueLock = new ReentrantLock();

    public static void readerFunction(List<Socket> sockets) {
        int readnum = 0;
        try {
            boolean done = false;
            while (!done) {
                done = true;
                for (int socketIndex = 0; socketIndex < sockets.size(); socketIndex++) {
                    if (locks[socketIndex].isLocked()) {
                        continue;
                    }
                    if (requested[socketIndex]) {
                        continue;
                    }
                    locks[socketIndex].lock();
                    done = false;
                    // System.out.println("Server: Socket " + socketIndex + " hasn't requested
                    // yet");
                    InputStream in = sockets.get(socketIndex).getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.equals("request")) {
                            // System.out.println(line + " from socket " + socketIndex);
                            requested[socketIndex] = true;
                            readnum++;
                            break;
                        }
                        String[] parts = line.split(" ");
                        int first = Integer.parseInt(parts[0]);
                        int second = Integer.parseInt(parts[1]);
                        try {
                            queueLock.lock();
                            numberQueue.put(new Object[] { first, second });
                            queueLock.unlock();
                            // System.out
                            // .println("Server: Reader thread - current queue size = " +
                            // numberQueue.size());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        readnum++;
                    }
                    locks[socketIndex].unlock();
                }
            }
            doneReading = true;
            // System.out.println("Server: Reader thread - done reading, read " + readnum +
            // " lines");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writerFunction() {
        try {
            while (!doneReading) {
                // System.out.println("Server: writing");
                if (!numberQueue.isEmpty()) {
                    // System.out.println("Server: Writer - LOCKING Queue");
                    queueLock.lock();
                    Object[] pair = numberQueue.take();
                    int first = (int) pair[0];
                    int second = (int) pair[1];
                    // System.out.println("Server: Writing thread - blocked queue + read pair " +
                    // first + " " + second);
                    // System.out.println("Server: Writer - UNLOCKING Queue");
                    queueLock.unlock();
                    scoreLock.lock();
                    boolean found = false;
                    for (Score score : scores) {
                        if (score.participantId == first) {
                            found = true;
                            if (score.score == -1 || second == -1) {
                                score.score = -1;
                            } else {
                                score.score += second;
                            }
                        }
                    }
                    if (!found) {
                        scores.add(new Score(first, second));
                    }
                    scoreLock.unlock();
                }

                Thread.sleep(50); // sleeping 50ms so we don't spam the blocking queue cause we don't have
                                  // conditional variables to wake us up here
            }
            // done reading, will empty numberQueue if there are numbers left
            // System.out.println("Server: Writing thread - done reading, will check 1 more
            // time for numberQueue");
            while (!numberQueue.isEmpty()) {
                queueLock.lock();
                Object[] pair = numberQueue.take();
                int first = (int) pair[0];
                int second = (int) pair[1];
                queueLock.unlock();
                scoreLock.lock();
                boolean found = false;
                for (Score score : scores) {
                    if (score.participantId == first) {
                        found = true;
                        if (score.score == -1 || second == -1) {
                            score.score = -1;
                        } else {
                            score.score += second;
                        }
                    }
                }
                if (!found) {
                    scores.add(new Score(first, second));
                }
                scoreLock.unlock();
            }
            // should be 500 scores in scores
            // System.out.println("Server: Writing thread - done with all writing; currently
            // have " + scores.size()
            // + " scores");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void calculateLeaderboard() {
        // lock the list of scores
        scoreLock.lock();
        // reset the leaderboard
        // System.out.println("Server: Resetting leaderboard");
        for (int i = 0; i < 5; i++) {
            leaderboard[i] = 0;
        }
        // calculate leaderboard
        for (Score score : scores) {
            if (score.score == -1) {
                continue;
            }
            leaderboard[(score.participantId - 1) / 100] += score.score;
        }
        // System.out.println("Server: Calculated leaderboard, updating
        // lastLeaderboardUpdate");
        lastLeaderboardUpdate = System.currentTimeMillis();
        scoreLock.unlock();
    }

    public static void sendLeaderboard(Socket socket) {
        // iterate through leaderboard and send the ints, 1 per line
        // send the leaderboard to the client
        try {
            // System.out.println("Server: Sending Leaderboard...");
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);

            // Send each element of the leaderboard array as a separate line
            for (int score : leaderboard) {
                writer.println(score);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void listenForRequests(List<Socket> sockets) {
        for (int i = 0; i < sockets.size(); i++) {
            sent[i] = false;
        }
        boolean done = false;
        while (!done) {

            // System.out.println("Server: looking for requests");
            done = true;
            for (int countryId = 0; countryId < sockets.size(); countryId++) {
                if (!requested[countryId]) {
                    done = false;
                } else {
                    if (!sent[countryId]) {

                        if (System.currentTimeMillis() - lastLeaderboardUpdate > dt) {
                            // System.out.println("Recalculating leaderboard");
                            CompletableFuture<Void> calculateFuture = CompletableFuture.runAsync(() -> {
                                calculateLeaderboard();
                            });
                            final int countryIdFinal = countryId;
                            calculateFuture.thenRun(() -> {

                                sendLeaderboard(sockets.get(countryIdFinal));
                                sent[countryIdFinal] = true;
                                // System.out.println("Server: Sent leaderboard to countryId " +
                                // countryIdFinal);

                            });

                            try {
                                // Wait for both calculations to complete
                                calculateFuture.get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        try {
            // System.out.println("Server: reading args");
            // read from args the following: p_r - int, p_w - int, dt - int
            p_r = Integer.parseInt(args[0]);
            p_w = Integer.parseInt(args[1]);
            dt = Integer.parseInt(args[2]);
            int numServers = Integer.parseInt(args[3]);
            for (int i = 0; i < numServers; i++) {
                locks[i] = new ReentrantLock();
            }

            List<Socket> sockets = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(8082);
            for (int i = 0; i < numServers; i++) {
                // System.out.println("Server: waiting for connection " + i);
                Socket socket = serverSocket.accept();
                sockets.add(socket);
            }
            // System.out.println("Server: all 5 connections done");
            Thread[] readerThreads = new Thread[p_r];
            Thread[] writerThreads = new Thread[p_w];
            // System.out.println("Server: creating reader threads");
            for (int i = 0; i < p_r; i++) {
                readerThreads[i] = new Thread(() -> readerFunction(sockets));
                readerThreads[i].start();
            }
            // System.out.println("Server: creating writer threads");
            for (int i = 0; i < p_w; i++) {
                writerThreads[i] = new Thread(() -> writerFunction());
                writerThreads[i].start();
            }
            // System.out.println("Server: entering listenForRequests function");
            listenForRequests(sockets);
            try {
                // System.out.println("Server: waiting to join reader threads");
                for (int i = 0; i < p_r; i++) {
                    readerThreads[i].join();
                }
                // System.out.println("Server: waiting to join writer threads");
                for (int i = 0; i < p_w; i++) {
                    writerThreads[i].join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println("Server: Writing files");
            createFinalFiles();
            // System.out.println("Server: Sending files");
            for (int i = 0; i < numServers; i++) {
                sendFile("results.txt", sockets.get(i));
                sendFile("leaderboard.txt", sockets.get(i));
            }
            // Thread.sleep(2000);
            for (int i = 0; i < numServers; i++) {
                sockets.get(i).close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFinalFiles() {
        // write to the individual files
        writeLeaderboardFile();
        writeCountryFile();

    }

    private static void writeLeaderboardFile() {
        // System.out.println("Server: Writing leaderboard file");
        // Calculate leaderboard once more
        calculateLeaderboard();

        // Write leaderboard.txt
        String fileName = "leaderboard.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int score : leaderboard) {
                writer.write(Integer.toString(score));
                writer.newLine();
            }
            // System.out.println("Server: Successfully wrote leaderboard to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCountryFile() {
        // System.out.println("Server: Writing country file");

        // Sort scores using the Score comparator
        Collections.sort(scores, Collections.reverseOrder());

        // Write scores to results.txt
        String fileName = "results.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Score score : scores) {
                writer.write("(" + score.participantId + ", " + score.score + ")");
                writer.newLine();
            }
            // System.out.println("Server: Successfully wrote country file to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(String fileName, Socket clientSocket) {
        try {
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeUTF(fileName);
            long fileLength = new File(fileName).length();
            outputStream.writeLong(fileLength);

            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            // OutputStream outputStream = clientSocket.getOutputStream();

            // // Send file name to client
            // PrintWriter writer = new PrintWriter(outputStream, true);
            // writer.println(fileName);
            // // Send file length in bytes to client
            // long fileLength = new File(fileName).length();
            // System.out.println("Server: File length = " + fileLength);
            // writer.println(fileLength);
            // // Send file content to client
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            // // Send ending message to client
            // writer.println("FileTransferDone");
            // System.out.println("Server: File '" + fileName + "' sent successfully.");

            // bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Score implements Comparable<Score> {
        public int participantId;
        public int score;

        public Score(int participantId, int score) {
            this.participantId = participantId;
            this.score = score;
        }

        @Override
        public int compareTo(Score other) {
            // Compare scores for sorting
            if (this.score == other.score) {
                return -1 * Integer.compare(this.participantId, other.participantId);
            }
            return Integer.compare(this.score, other.score);
        }
    }
}
