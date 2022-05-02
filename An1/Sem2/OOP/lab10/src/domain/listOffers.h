#ifndef LISTOFFERS_H_
#define LISTOFFERS_H_

#include "offer.h"
// #include "VectorDinamic.h"
#include <vector>
#include <algorithm>

class listOffers
{
private:
    std::vector<Offer> offers;

public:
    listOffers();

    ~listOffers();

    std::vector<Offer> &getOffers();

    /**
     * @brief Add a new offer to the list
     *
     * @param offer
     */
    void addOffer(const Offer &offer);

    /**
     * @brief Modify a given offer, identified by its name
     *
     * @param offer
     */
    void modifyOffer(const Offer &oldOffer, const Offer &newOffer);

    /**
     * @brief Remove a given offer, identified by its name
     *
     * @param offer
     */
    void removeOffer(const Offer &offer);

    /**
     * @brief Compare 2 offers to see if they're the same; we consider them equal if they have the same name
     *
     * @param offer1
     * @param offer2
     * @return true : if their names are the same
     * @return false : if their names are not the same
     */
    // const bool compareOffers(const Offer &offer1, const Offer &offer2) const;

    /**
     * @brief Search for an offer with a given name
     *
     * @param name
     * @return Offer& : if it exists, else NULL
     */
    const Offer &searchOffer(const std::string &name) const;
    /**
     * @brief If an offer exists, identified by name, return true; else false
     *
     * @param offer
     * @return true : if exists
     * @return false : if not exists
     */
    bool checkOfferExists(const Offer &offer) const;

    /**
     * @brief Check if offer list is empty
     *
     * @return true
     * @return false
     */
    bool isEmpty() const;

    /**
     * @brief Return length of repo
     *
     * @return const int
     */
    const int dim() const;

    // listOffers &operator=(const listOffers &other)
    // {
    //     for (auto it = other.offers.begin(); it != other.offers.end(); it++)
    //     {
    //         offers.push_back((*it));
    //     }
    //     return *this;
    // }
};

#endif