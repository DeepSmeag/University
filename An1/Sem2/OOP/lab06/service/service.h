#ifndef SERVICE_H_
#define SERVICE_H_

#include "../repo/repo.h"
#include "undo.h"
#include <random>
#include <ctime>
#include <array>
#include <memory>
// #include "../valid/valid.h"

class Service
{
private:
    Repository repo;
    std::vector<std::unique_ptr<UndoAction>> undoActions;

public:
    Service();
    ~Service();

    /**
     * @brief
     *
     * @return int: 0-good; 1-error on add
     */
    void serviceAdd(const std::string &name, const std::string &destination, const std::string &type, const std::string &price);

    /**
     * @brief Service function for Modify
     *
     * @return int: 0-good; 1-error on modify
     */
    void serviceModify(const std::string &name, const std::string &newName, const std::string &newDestination, const std::string &newType, const std::string &newPrice);

    /**
     * @brief Service function for Remove
     *
     * @return int: 0-good; 1-error on remove
     */
    void serviceRemove(const std::string &name);

    /**
     * @brief Search for an Offer given its name
     *
     * @param name
     * @return const Offer&
     */
    const Offer &serviceSearch(const std::string &name);

    /**
     * @brief Service function for Filter
     *
     */
    listOffers serviceFilter(const std::string &criterion, const std::string &field);

    /**
     * @brief These functions compare the offers in their each respective fields and return
     *
     * @param offer1
     * @param offer2
     * @return const int >0: offer1 > offer2 ; <0: offer1 < offer2
     */
    // const int serviceSortName(Offer &offer1, Offer &offer2) const;
    // const int serviceSortDestination(Offer &offer1, Offer &offer2) const;
    // const int serviceSortType(Offer &offer1, Offer &offer2) const;
    // const int serviceSortPrice(Offer &offer1, Offer &offer2) const;
    // const int serviceSortNull(Offer &offer1, Offer &offer2) const;

    /**
     * @brief Service function for sort
     *
     */
    listOffers serviceSort(const std::string &criterion, const std::string &order);

    /**
     * @brief Service utility to get all offers so they can be printed in UI
     *
     */
    listOffers &serviceGetAll();
    Cart &serviceCartGetAll();
    /**
     * @brief Get current dimension of repo
     *
     * @return const int
     */
    const int serviceLen() const;
    int serviceGenerate(std::string number);
    Offer serviceRandomOffer();
    int serviceCartGenerate(std::string number);
    void serviceCartAdd(std::string name);
    void serviceCartClear();
    void serviceExportCart(std::string fileName);

    Report createReport();
    void serviceUndo();
};

bool serviceSortName(Offer &offer1, Offer &offer2);
bool serviceSortDestination(Offer &offer1, Offer &offer2);
bool serviceSortType(Offer &offer1, Offer &offer2);
bool serviceSortPrice(Offer &offer1, Offer &offer2);
bool serviceSortNull(Offer &offer1, Offer &offer2);

/**
 * @brief Function to decide if the offer passes the filter for destination
 *
 * @param offer
 * @return true
 * @return false
 */
bool serviceFilterDestination(Offer &offer, const std::string &field);
/**
 * @brief Function to decide if the offer passes the filter for price
 *
 * @param offer
 * @return true
 * @return false
 */
bool serviceFilterPrice(Offer &offer, const std::string &field);

std::string generateRandomString(int length);
std::string generateRandomNumberString(int length);

#endif