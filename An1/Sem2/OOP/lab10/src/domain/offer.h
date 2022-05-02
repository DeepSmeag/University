#ifndef OFFER_H_
#define OFFER_H_

#include <string>
#include <iostream>
class Offer
{
private:
    std::string name;
    std::string destination;
    std::string type;
    std::string price;

public:
    Offer();
    Offer(const std::string &name, const std::string &destination, const std::string &type, const std::string &price);
    Offer(const Offer &offer);
    ~Offer();

    /**
     * @brief Get and Set the Name object
     *
     * @return std::string&
     */
    const std::string &getName() const;
    void setName(const std::string &name);

    /**
     * @brief Get and Set the Destination object
     *
     * @return std::string&
     */
    const std::string &getDestination() const;
    void setDestination(const std::string &destination);

    /**
     * @brief Get and Set the Type object
     *
     * @return std::string&
     */
    const std::string &getType() const;
    void setType(const std::string &type);

    /**
     * @brief Get and Set the Price object
     *
     * @return int&
     */
    const std::string &getPrice() const;
    void setPrice(const std::string &price);

    bool operator==(const Offer &rhs) const;
    Offer &operator=(const Offer &other);
    /**
     * @brief Print the object in a nice format: Name - Destination - Type - Price
     *
     */
    const std::string printFormat() const;
};

#endif