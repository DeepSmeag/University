#include "../repo/repo.h"

class UndoAction
{
public:
    virtual void doUndo() = 0;
    virtual ~UndoAction() = default;

    UndoAction() = default;
    UndoAction(UndoAction &other) = default;
    UndoAction &operator=(const UndoAction &other) = default;
    UndoAction(UndoAction &&other) = default;
    UndoAction &operator=(UndoAction &&other) = default;
};

class UndoAdd : public UndoAction
{
private:
    Repository &repo;
    Offer offerAdded;

public:
    UndoAdd(Repository &repo, const Offer &offer) : repo{repo}, offerAdded{offer} {}
    void doUndo() override
    {
        repo.repoRemove(offerAdded.getName());
    }
};

class UndoRemove : public UndoAction
{
private:
    Repository &repo;
    Offer offerRemoved;

public:
    UndoRemove(Repository &repo, const Offer &offer) : repo{repo}, offerRemoved{offer} {}
    void doUndo() override
    {
        repo.repoAdd(offerRemoved.getName(), offerRemoved.getDestination(), offerRemoved.getType(), offerRemoved.getPrice());
    }
};
class UndoModify : public UndoAction
{
private:
    Repository &repo;
    Offer offerOld, offerNew;

public:
    UndoModify(Repository &repo, const Offer &oldOffer, const Offer &newOffer) : repo{repo}, offerOld{oldOffer}, offerNew{newOffer} {}
    void doUndo() override
    {
        repo.repoModify(offerNew.getName(), offerOld.getName(), offerOld.getDestination(), offerOld.getType(), offerOld.getPrice());
    }
};