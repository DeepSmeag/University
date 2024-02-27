// test.h
#pragma once

#include <iostream>
#include <iomanip>
#include <string>
#include <vector>
#include <tuple>

class Node
{
public:
    std::string data;
    int id;
    std::string category;
    Node *left;
    Node *right;

    Node(const std::string &value, const std::string category) : data(value), id(-1), category(category), left(nullptr), right(nullptr) {}
    int getId();
    void setId(int id);
    std::string getCategory();
};

class BinarySearchTree
{
private:
    Node *root;

    // Helper functions
    Node *insertRecursive(Node *current, const std::string &value, const std::string category);
    Node *searchRecursive(Node *current, const std::string &value) const;
    void inorderTraversalRecursive(Node *current, int &id, std::vector<std::tuple<std::string, std::string, int, std::string, int>> &fip) const;

public:
    BinarySearchTree() : root(nullptr) {}

    // Public methods
    void insert(const std::string &value, std::string category);
    Node *search(const std::string &value) const;
    void inorderTraversal(std::vector<std::tuple<std::string, std::string, int, std::string, int>> &fip) const;
};
