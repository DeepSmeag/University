#include "listOffers.h"

class Cart : public listOffers
{
private:
    std::vector<Offer> offers;

public:
    Cart();

    Cart(const Cart &other);

    ~Cart();
    // std::vector<Offer> &getCartOffers();

    Cart &operator=(const Cart &other) = default;

    Cart(Cart &&other) = default;

    Cart &operator=(Cart &&other) = default;

    void cartClear();
    void addOffer(const Offer &offer);
    std::vector<Offer> &getOffers();
    bool checkOfferExists(const Offer &offer) const;
    bool isEmpty() const;
    const int dim() const;
};