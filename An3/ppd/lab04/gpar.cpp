#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <queue>
#include <mutex>
#include <thread>
#include <condition_variable>

using namespace std;
// Structure to represent a participant's result
struct ParticipantResult
{
    int ID;
    int Score;

    ParticipantResult(int id, int score) : ID(id), Score(score) {}
};

// Comparison function for sorting the linked list
bool compareResults(const ParticipantResult &a, const ParticipantResult &b)
{
    return a.Score > b.Score;
}

// Node structure for the linked list
struct Node
{
    ParticipantResult data;
    Node *next;
    // mutex
    std::mutex nodeMutex;

    Node(int id, int score) : data(id, score), next(nullptr) {}
};

// Linked list class
class LinkedList
{
private:
    Node *head;
    std::mutex listMutex;
    Node *searchNode(int id)
    {
        Node *current = head;

        while (current != nullptr && current->data.ID != id)
        {
            std::lock_guard<std::mutex> lock(current->nodeMutex);
            current = current->next;
        }

        return current; // Return the node before the found node (or nullptr if not found)
    }

public:
    LinkedList() : head(nullptr) {}

    // Function to insert a new node into the linked list
    void insertNode(int id, int score)
    {
        Node *current = searchNode(id);
        if (current != nullptr)
        {
            std::lock_guard<std::mutex> lock(current->nodeMutex);
            if (current->data.Score == -1 || score == -1)
            {
                current->data.Score = -1;
            }
            else
            {
                current->data.Score += score;
            }
        }
        else
        {
            std::lock_guard<std::mutex> lock(listMutex);
            if (head == nullptr)
            {
                head = new Node(id, score);
            }
            else
            {
                std::lock_guard<std::mutex> lock(head->nodeMutex);
                Node *newNode = new Node(id, score);
                newNode->next = head;
                head = newNode;
            }
        }
    }
    Node *getHead()
    {
        return head;
    }
    // Function to sort the linked list in descending order based on scores
    void sortList()
    {
        if (head == nullptr || head->next == nullptr)
        {
            return; // Already sorted
        }

        Node *sorted = nullptr;

        while (head != nullptr)
        {
            Node *current = head;
            head = head->next;

            if (sorted == nullptr || current->data.Score >= sorted->data.Score)
            {
                current->next = sorted;
                sorted = current;
            }
            else
            {
                Node *temp = sorted;
                while (temp->next != nullptr && temp->next->data.Score > current->data.Score)
                {
                    temp = temp->next;
                }
                current->next = temp->next;
                temp->next = current;
            }
        }

        head = sorted;
    }

    // Function to print the linked list
    void printList(const std::string &outputFile)
    {
        std::lock_guard<std::mutex> lock(listMutex);

        std::ofstream outFile(outputFile);
        if (outFile.is_open())
        {
            Node *current = head;
            while (current)
            {
                outFile << "(" << current->data.ID << ", " << current->data.Score << ")\n";
                current = current->next;
            }
            outFile.close();
        }
        else
        {
            std::cerr << "Error: Unable to open output file." << std::endl;
        }
    }

    ~LinkedList()
    {
        Node *current = head;
        Node *next;

        while (current != nullptr)
        {
            next = current->next;
            delete current;
            current = next;
        }
    }
};

// Function to read results from a file and push them into the queue
void readerFunction(const std::vector<std::string> &filenames, int threadId, std::queue<ParticipantResult> &resultsQueue, std::mutex &queueMutex, std::condition_variable &cv, std::condition_variable &cv2)
{
    // cout << "Reader thread " << std::this_thread::get_id() << " is reading from index " << startIndex << "to" << startIndex + count << endl;
    for (int fileIndex = 50 / 4 * threadId; fileIndex < 50 / 4 * threadId + 50 / 4; fileIndex++)
    {
        // cout << "Now reading index " << fileIndex << endl;
        // cout reader thread id is reading from file
        // cout << "Reader thread " << std::this_thread::get_id() << " is reading from file " << filenames[fileIndex] << endl;
        std::ifstream inFile(filenames[fileIndex]);
        if (!inFile.is_open())
        {
            std::cerr << "Error: Unable to open file " << filenames[fileIndex] << std::endl;
            return;
        }

        int id, score;
        char sep;
        while (inFile >> sep >> id >> sep >> score >> sep)
        {
            std::unique_lock<std::mutex> lock(queueMutex);
            cv2.wait(lock, [&resultsQueue]
                     { return resultsQueue.size() < 100; });
            resultsQueue.push(ParticipantResult(id, score));
            lock.unlock();
            cv.notify_one();
        }

        inFile.close();
    }
    if (threadId == 3)
    {
        for (int fileIndex = 48; fileIndex < 50; fileIndex++)
        {
            std::ifstream inFile(filenames[fileIndex]);
            if (!inFile.is_open())
            {
                std::cerr << "Error: Unable to open file " << filenames[fileIndex] << std::endl;
                return;
            }

            int id, score;
            char sep;
            while (inFile >> sep >> id >> sep >> score >> sep)
            {
                std::unique_lock<std::mutex> lock(queueMutex);
                cv2.wait(lock, [&resultsQueue]
                         { return resultsQueue.size() < 100; });
                resultsQueue.push(ParticipantResult(id, score));
                lock.unlock();
                cv.notify_one();
            }
        }
    }
}

// Function to process results from the queue and update the linked list
void workerFunction(std::queue<ParticipantResult> &resultsQueue, LinkedList &resultList, std::mutex &queueMutex, std::condition_variable &cv, bool &finished, std::condition_variable &cv2)
{
    while (true)
    {
        std::unique_lock<std::mutex> lock(queueMutex);
        cv.wait(lock, [&resultsQueue, &finished]
                { return !resultsQueue.empty() || finished; });
        // cout << "Worker thread " << std::this_thread::get_id() << " is awake" << endl;
        if (!resultsQueue.empty())
        {
            // worker id is inserting result into linked list
            // cout << "Worker thread " << std::this_thread::get_id() << " is inserting result into linked list" << endl;
            ParticipantResult result = resultsQueue.front();
            resultsQueue.pop();
            cv2.notify_one();
            lock.unlock();

            // Process the result (e.g., add it to the linked list)
            resultList.insertNode(result.ID, result.Score);
            // cout << "Worker thread " << std::this_thread::get_id() << " is done inserting result into linked list" << endl;
        }
        else if (finished)
        {
            break;
        }
    }
}

int main(int argc, char **argv)
{
    // Number of threads
    const int p = atoi(argv[1]);
    const int N = atoi(argv[2]);
    if (argc != 3)
    {
        cout << "Usage: ./par <p> <N>" << endl;
        return 1;
    }
    if (p <= N)
    {
        cout << "p must be greater than N" << endl;
        return 1;
    }
    // File names
    std::vector<std::string> filenames = {
        "RezultateC0_P1.txt",
        "RezultateC0_P2.txt",
        "RezultateC0_P3.txt",
        "RezultateC0_P4.txt",
        "RezultateC0_P5.txt",
        "RezultateC0_P6.txt",
        "RezultateC0_P7.txt",
        "RezultateC0_P8.txt",
        "RezultateC0_P9.txt",
        "RezultateC0_P10.txt",
        "RezultateC1_P1.txt",
        "RezultateC1_P2.txt",
        "RezultateC1_P3.txt",
        "RezultateC1_P4.txt",
        "RezultateC1_P5.txt",
        "RezultateC1_P6.txt",
        "RezultateC1_P7.txt",
        "RezultateC1_P8.txt",
        "RezultateC1_P9.txt",
        "RezultateC1_P10.txt",
        "RezultateC2_P1.txt",
        "RezultateC2_P2.txt",
        "RezultateC2_P3.txt",
        "RezultateC2_P4.txt",
        "RezultateC2_P5.txt",
        "RezultateC2_P6.txt",
        "RezultateC2_P7.txt",
        "RezultateC2_P8.txt",
        "RezultateC2_P9.txt",
        "RezultateC2_P10.txt",
        "RezultateC3_P1.txt",
        "RezultateC3_P2.txt",
        "RezultateC3_P3.txt",
        "RezultateC3_P4.txt",
        "RezultateC3_P5.txt",
        "RezultateC3_P6.txt",
        "RezultateC3_P7.txt",
        "RezultateC3_P8.txt",
        "RezultateC3_P9.txt",
        "RezultateC3_P10.txt",
        "RezultateC4_P1.txt",
        "RezultateC4_P2.txt",
        "RezultateC4_P3.txt",
        "RezultateC4_P4.txt",
        "RezultateC4_P5.txt",
        "RezultateC4_P6.txt",
        "RezultateC4_P7.txt",
        "RezultateC4_P8.txt",
        "RezultateC4_P9.txt",
        "RezultateC4_P10.txt",
        // Add more file names as needed
    };

    // Linked list to store results
    LinkedList resultList;

    // Queue to store results temporarily before adding to the linked list
    std::queue<ParticipantResult> resultsQueue;

    // Mutex and condition variable for synchronization
    std::mutex queueMutex;
    std::condition_variable cv;
    std::condition_variable cv2;

    // Flag to indicate that all threads have finished reading
    bool finished = false;

    auto start = std::chrono::high_resolution_clock::now();

    // Create reader threads
    std::vector<std::thread> readerThreads;
    for (int i = 0; i < N; ++i)
    {

        readerThreads.emplace_back(readerFunction, filenames, i, std::ref(resultsQueue), std::ref(queueMutex), std::ref(cv), std::ref(cv2));
    }

    // Create worker threads
    std::vector<std::thread> workerThreads;
    for (int i = 0; i < p - N; ++i)
    {

        workerThreads.emplace_back(workerFunction, std::ref(resultsQueue), std::ref(resultList), std::ref(queueMutex), std::ref(cv), std::ref(finished), std::ref(cv2));
    }

    // Wait for all reader threads to finish
    for (auto &thread : readerThreads)
    {
        thread.join();
    }
    // Notify worker threads that no more results will be added
    {
        std::lock_guard<std::mutex> lock(queueMutex);
        finished = true;
    }
    // cout << "waking up workers" << endl;
    cv.notify_all();

    // Wait for all worker threads to finish
    for (auto &thread : workerThreads)
    {
        thread.join();
    }

    // Sort the linked list in descending order
    resultList.sortList();
    resultList.printList("ClasamentPar.txt");
    auto end = std::chrono::high_resolution_clock::now();
    cout << std::chrono::duration_cast<std::chrono::nanoseconds>(end - start).count() / 1e6 << endl;
    return 0;
}
