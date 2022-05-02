#ifndef REPO_H_
#define REPO_H_

#include "../domain/listOffers.h"
#include "../domain/cart.h"
#include "../errors/errors.h"
#include "../domain/report.h"
#include <fstream>
class Repository
{

private:
    listOffers offerList;
    Cart cartList;

public:
    Repository();

    /**
     * @brief Repo function for add
     *
     * @param offer
     * @return int : 0 successful; 1- not
     */
    void repoAdd(const std::string &name, const std::string &destination, const std::string &type, const std::string &price);

    /**
     * @brief Repo function for modify
     *
     * @param offer
     * @return int : 0 successful; 1- not
     */
    void repoModify(const std::string &name, const std::string &newName, const std::string &newDestination, const std::string &newType, const std::string &newPrice);

    /**
     * @brief Repo function for remove
     *
     * @param offer
     * @return int : 0 successful; 1- not
     */
    void repoRemove(const std::string &name);

    /**
     * @brief Return offer if it exists
     *
     * @param name
     * @return const Offer&
     */
    const Offer &repoSearchByName(const std::string &name);
    /**
     * @brief Check if an offer exists
     *
     * @param offer
     * @return true : if exists
     * @return false : if not exists
     */
    const bool repoOfferExists(const Offer &offer) const;
    const bool repoCartOfferExists(const Offer &offer) const;
    /**
     * @brief Get the listOffers object
     *
     * @return listOffers&
     */
    listOffers &getOffersListObject();
    Cart &getCartOffersListObject();
    /**
     * @brief Get a copy of the listOffers object
     *
     * @return listOffers&
     */
    listOffers getOffersListObjectCopy();

    /**
     * @brief Return the length of the repo
     *
     * @return const int
     */
    const int repoLen() const;

    void repoCartAdd(const Offer &offer);
    void repoCartClear();
    void repoExportCart(std::string fileName);
    ~Repository();
};

class RepositoryFile
{
private:
    Repository &repo;
    std::string fName;

public:
    RepositoryFile(Repository &repo, std::string fileName) : repo{repo}, fName{fileName} {}
    void ExportCart();
};

#endif