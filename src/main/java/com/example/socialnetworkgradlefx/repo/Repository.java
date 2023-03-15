package com.example.socialnetworkgradlefx.repo;

import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;

import java.util.List;

/**
 * Interface for repository
 * @param <EntityType>
 */
public interface Repository<EntityType> {
    void add(EntityType entity) throws RepoException;
    void remove(EntityType entity);
    void update(EntityType entity) throws RepoException;
    void updateWithoutException(EntityType entity);
    EntityType getById(int id);
    List<EntityType> getAll();
    int sizee();
}

