package com.example.socialnetworkgradlefx.repo.file;

import com.example.socialnetworkgradlefx.domain.Entity;
import com.example.socialnetworkgradlefx.repo.exceptions.RepoException;
import com.example.socialnetworkgradlefx.repo.memory.MemoryRepo;

import java.io.*;
import java.util.List;

/**
 * Class of file repository
 * @param <EntityType> entity
 */
public abstract class FileRepo<EntityType extends Entity> extends MemoryRepo<EntityType> {
    private final String fileName;

    /**
     * FileRepo constructor
     * @param fileName String - name of text file
     */
    public FileRepo(String fileName) {
        this.fileName = fileName;
        loadFromFile();
    }

    /**
     * Transforms string to entity
     * @param line String
     * @return EntityType
     */
    protected abstract EntityType lineToEntity(String line);

    /**
     * Transforms entity to a string
     * @param entity Entity
     * @return String
     */
    protected abstract String entityToLine(EntityType entity);

    public abstract boolean alreadyExists(EntityType entity);

    /**
     * Adds entity
     * @param entity EntityType
     * @throws RepoException if the entity we want to add already exists
     */
    @Override
    public void add(EntityType entity) throws RepoException{
        super.add(entity);
        saveToFile(super.getAll());
    }

    /**
     * Updates entity
     * @param entity EntityType
     */
    @Override
    public void update(EntityType entity) throws RepoException{
        super.update(entity);
        saveToFile(super.getAll());
    }

    /**
     * Update without verifying if the entity already exists
     * @param entity EntityType
     */
    @Override
    public void updateWithoutException(EntityType entity) {
        super.updateWithoutException(entity);
        saveToFile(super.getAll());
    }

    /**
     * Loads list of entities from text file
     */
    protected void loadFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    super.add(lineToEntity(line));
                } catch (RuntimeException ignored) {
                    System.out.println(ignored.getMessage());
                }
                catch (RepoException ex){
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Saves list of entities to file
     * @param entities List
     */
    protected void saveToFile(List<EntityType> entities) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileName, false))) {
            for (EntityType entity : entities) {
                bufferedWriter.write(entityToLine(entity));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Remove an entity
     * @param entity EntityType
     */
    @Override
    public void remove(EntityType entity) {
        super.remove(entity);
        saveToFile(super.getAll());
    }


    /**
     * Get an entity by id
     * @param id Int
     * @return EntityType
     */
    @Override
    public EntityType getById(int id) {
        return super.getById(id);
    }

    /**
     * Get all entities
     * @return List
     */
    @Override
    public List<EntityType> getAll() {
        return super.getAll();
    }

    /**
     * Get size of entities list
     * @return Integer
     */
    @Override
    public int sizee(){return super.sizee();}
}