// external_code.cpp
#include "test.h"

// Implementation of helper functions
int Node::getId()
{
    return id;
}
void Node::setId(int id)
{
    this->id = id;
}
std::string Node::getCategory()
{
    return category;
}

Node *BinarySearchTree::insertRecursive(Node *current, const std::string &value, const std::string category)
{
    if (current == nullptr)
    {
        return new Node(value, category);
    }

    if (value < current->data)
    {
        current->left = insertRecursive(current->left, value, category);
    }
    else if (value > current->data)
    {
        current->right = insertRecursive(current->right, value, category);
    }

    return current;
}

Node *BinarySearchTree::searchRecursive(Node *current, const std::string &value) const
{
    if (current == nullptr)
    {
        return nullptr;
    }

    if (value == current->data)
    {
        return current;
    }
    else if (value < current->data)
    {
        return searchRecursive(current->left, value);
    }
    else
    {
        return searchRecursive(current->right, value);
    }
}

void BinarySearchTree::inorderTraversalRecursive(Node *current, int &id, std::vector<std::tuple<std::string, std::string, int, std::string, int>> &fip) const
{
    if (current != nullptr)
    {
        inorderTraversalRecursive(current->left, id, fip);

        // std::cout << std::setw(50) << current->getCategory() << " " << current->data << " " << id << std::endl;
        fip.push_back(std::make_tuple(current->getCategory(), current->data, id, "---", -1));
        current->setId(id);
        id++;
        inorderTraversalRecursive(current->right, id, fip);
    }
}

// Implementation of public methods

void BinarySearchTree::insert(const std::string &value, std::string category)
{
    root = insertRecursive(root, value, category);
}

Node *BinarySearchTree::search(const std::string &value) const
{
    return searchRecursive(root, value);
}

void BinarySearchTree::inorderTraversal(std::vector<std::tuple<std::string, std::string, int, std::string, int>> &fip) const
{
    int id = 0;
    inorderTraversalRecursive(root, id, fip);
    std::cout << std::endl;
}

// Example usage