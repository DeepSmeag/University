package com.example.lab09forward.repository.memory;

import com.example.lab09forward.domain.Entities.Entity;
import com.example.lab09forward.domain.Entities.User;

import com.example.lab09forward.domain.validators.Validator;
import com.example.lab09forward.repository.Repository0;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class models InMemoryRepository0 type of repository
 * @param <ID> - type of id
 * @param <E> - type of entity
 */
public class InMemoryRepository0<ID, E extends Entity<ID>> implements Repository0<ID,E> {
    /**
     * validator class member
     */
    private Validator<E> validator;
    /**
     * entities class member
     */
    Map<ID,E> entities;

    /**
     * Constructor for InMemoryRepository0
     * @param validator - Validator
     */
    public InMemoryRepository0(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    /**
     * Method to find an entity by id
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return - the entity with the specified id
     */
    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return entities.get(id);
    }

    /**
     * Method to get all entities
     * @return - all entities as an Iterable
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     * Method to save an entity
     * @param entity - entity must be not null
     * @return - null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    @Override
    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        if(entities.get(entity.getId()) != null) {
            return entity;
        }
        // Saving in memory
        entities.put(entity.getId(),entity);
        return null;
    }

    /**
     * Method to delete an entity by id
     * @param id
     *      id must be not null
     * @return - the removed entity or null if there is no entity with the given id
     */
    @Override
    public E delete(ID id) {
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        E entity = entities.get(id);
        entities.remove(id);
        return entity;
    }

    /**
     * Method to update an entity
     * @param entity
     *          entity must not be null
     * @return - null- if the entity is updated, otherwise returns the entity - (e.g id does not exist).
     */
    @Override
    public E update(E entity) {

        if(entity == null)
            throw new IllegalArgumentException("entity must be not null!");
        validator.validate(entity);

        entities.put(entity.getId(),entity);

        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;

    }

}
