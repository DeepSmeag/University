#include <iostream>
#include <chrono>
#include <random>
#include <fstream>
using namespace std;

// first need to generate the input files
// 500 participants from 5 countries, 100 each
// country has the same 10 problems; each participant gets a score for each problem
// scores are integers between 0 and 10; there is a chance of 2% that a participant gets a score of -1
// -1 means that the participant attempted fraud
// the files will have the format RezultateCx_Py.txt, where x is the country number and y is the problem number
// each file will contain 100 lines of the format: (ID, score) where id is the id of that participant
// the id is a number between 1 and 500
// the files will be generated in the same folder as the program

// generate a random number between 0 and 10 with a 2% chance of generating -1

struct ParticipantResult
{
    int ID;
    int Score;

    ParticipantResult(int id, int score) : ID(id), Score(score) {}
};
struct Node
{
    ParticipantResult data;
    Node *next;

    Node(int id, int score) : data(id, score), next(nullptr) {}
}; // Linked list class
class LinkedList
{
private:
    Node *head;

public:
    LinkedList() : head(nullptr) {}

    // Function to insert a new node into the linked list
    void insertNode(int id, int score)
    {
        Node *current = head;
        Node *prev = nullptr;

        // Check if the node with the same ID already exists
        while (current != nullptr && current->data.ID != id)
        {
            prev = current;
            current = current->next;
        }

        if (current == nullptr)
        {
            // Node with ID not found, create a new node
            Node *newNode = new Node(id, score);
            newNode->next = head;
            head = newNode;
        }
        else
        {
            if (current->data.Score == -1)
            {
                // Node with ID found, but the score is -1, so we don't update it
                return;
            }
            // Node with ID found
            if (score >= 0)
            {
                // Add the score to the existing score
                current->data.Score += score;
            }
            else
            {
                // Set the score to -1
                current->data.Score = -1;
            }
        }
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
        std::ofstream outFile(outputFile);
        if (outFile.is_open())
        {
            Node *current = head;
            while (current != nullptr)
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

int generateScore()
{
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dis(0, 100);
    int score = dis(gen);
    if (score < 2)
        return -1;
    uniform_int_distribution<> dis2(0, 10);
    score = dis2(gen);
    return score;
}
// void generateInput()
// {
//     // generate the input files
//     for (int i = 0; i < 5; i++)
//     {
//         for (int j = 1; j <= 10; j++)
//         {
//             string filename = "RezultateC" + to_string(i) + "_P" + to_string(j) + ".txt";
//             ofstream fout(filename);
//             for (int k = 1; k <= 100; k++)
//             {
//                 int score = generateScore();
//                 fout << "(" << i * 100 + k << "," << score << ")" << endl;
//             }
//             fout.close();
//         }
//     }
// }

// Function to read files and update the linked list
void readAndUpdateList(LinkedList &resultList)
{
    for (int x = 0; x < 5; ++x)
    {
        for (int y = 1; y <= 10; ++y)
        {
            std::string filename = "RezultateC" + std::to_string(x) + "_P" + std::to_string(y) + ".txt";
            std::ifstream inFile(filename);

            if (inFile.is_open())
            {
                int id, score;
                char delimiter;

                while (inFile >> delimiter >> id >> delimiter >> score >> delimiter)
                {
                    resultList.insertNode(id, score);
                }

                inFile.close();
            }
            else
            {
                std::cerr << "Error: Unable to open file " << filename << std::endl;
            }
        }
    }
}

int main()
{
    LinkedList resultList;
    auto start = chrono::high_resolution_clock::now();

    // Add some results to the linked list
    readAndUpdateList(resultList);

    // Sort the linked list based on scores
    resultList.sortList();

    // Print the sorted linked list to a file
    resultList.printList("Clasament.txt");
    auto end = chrono::high_resolution_clock::now();
    auto duration = chrono::duration_cast<chrono::nanoseconds>(end - start);
    cout << duration.count() / 1e6 << endl;
    return 0;
}