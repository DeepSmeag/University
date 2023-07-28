package contests.persistence.interfaces;

import java.util.List;

public interface RepoInterface<EntityType,EntityId> {
    List<EntityType> getAll();

    EntityType getOne(EntityId id);

    boolean saveEntity(EntityType entity);

    boolean deleteEntity(EntityId id);

    boolean updateEntity(EntityType entity);
}
