package com.example.socialnetworkgradlefx.repo.memory;

import com.example.socialnetworkgradlefx.domain.Entity;
import com.example.socialnetworkgradlefx.repo.Repository;
import com.example.socialnetworkgradlefx.repo.exceptions.EntityAlreadyExistsException;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryRepo<EntityType extends Entity> implements Repository<EntityType> {

    private Map<Integer, EntityType> entities = new HashMap<>();

    /**
     * Verifies if an entity already exists
     * @param entity Entity
     * @return boolean - true if it already exists, false otherwise
     */
    public boolean alreadyExists(EntityType entity){
        List<EntityType> entities = getAll();
        for (EntityType e : entities){
            if(entity.equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an entity to the list
     * @param entity EntityType
     * @throws RepoException if the Entity we want to add already exists
     */
    public void add(EntityType entity) throws RepoException {
        if (alreadyExists(entity))
            throw new EntityAlreadyExistsException();
        entities.put(entity.getId(), entity);
    }

    /**
     * Removes an entity
     * @param entity EntityType
     */
    @Override
    public void remove(EntityType entity) {
        entities.remove(entity.getId());
    }

    /**
     * Updates an entity
     * @param entity EntityType
     * @throws RepoException if the Entity we want to update does not exist
     */
    @Override
    public void update(EntityType entity) throws RepoException {
        if (alreadyExists(entity))
            throw new EntityAlreadyExistsException();
        entities.put(entity.getId(), entity);
    }

    /**
     * Updates a Friendship
     * @param entity EntityType
     */
    @Override
    public void updateWithoutException(EntityType entity) {
        entities.put(entity.getId(), entity);
    }

    /**
     * Gets entity by id
     * @param id Integer
     * @return EntityType
     */
    @Override
    public EntityType getById(int id) {
        return entities.get(id);
    }

    /**
     * Gets all entities
     * @return List
     */
    @Override
    public List<EntityType> getAll() {
        return new ArrayList<>(entities.values());
    }

    /**
     * Returns size of entities list
     * @return Integer
     */
    @Override
    public int sizee(){return entities.size();}
}