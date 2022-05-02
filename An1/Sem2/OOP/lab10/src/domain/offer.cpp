#include "offer.h"

Offer::Offer() : name{""}, destination{""}, type{""}, price{""} {}

Offer::Offer(const std::string &name, const std::string &destination, const std::string &type, const std::string &price) : name{name}, destination{destination}, type{type}, price{price}
{
    // this->name = name;
    // this->destination = destination;
    // this->type = type;
    // this->price = price;
}
Offer::Offer(const Offer &offer) : name{offer.name}, destination{offer.destination}, type{offer.type}, price{offer.price}
{
    // this->name = offer.name;
    // this->destination = offer.destination;
    // this->type = offer.type;
    // this->price = offer.price;
    // std::cout << "called";
}

Offer::~Offer() {}

const std::string &Offer::getName() const
{
    return this->name;
}
void Offer::setName(const std::string &name)
{
    this->name.assign(name);
}

const std::string &Offer::getDestination() const
{
    return this->destination;
}
void Offer::setDestination(const std::string &destination)
{
    this->destination.assign(destination);
}

const std::string &Offer::getType() const
{
    return this->type;
}
void Offer::setType(const std::string &type)
{
    this->type.assign(type);
}

const std::string &Offer::getPrice() const
{
    return this->price;
}
void Offer::setPrice(const std::string &price)
{
    this->price.assign(price);
}

const std::string Offer::printFormat() const
{
    std::string format = this->getName() + " - " + this->getDestination() + " - " + this->getType() + " - " + this->getPrice() + "\n";
    return format;
}

Offer &Offer::operator=(const Offer &other)
{
    name = other.name;
    destination = other.destination;
    type = other.type;
    price = other.price;
    return *this;
}
bool Offer::operator==(const Offer &rhs) const
{
    return name.compare(rhs.getName()) == 0;
}