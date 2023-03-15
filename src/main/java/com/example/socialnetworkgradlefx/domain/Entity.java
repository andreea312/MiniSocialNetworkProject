package com.example.socialnetworkgradlefx.domain;

import java.util.Objects;
import java.util.Random;

/**
 * Class that represents an entity, in our case it will be a User or a Friendship
 */
public class Entity {
    /**
     * id of entity
     */
    protected final int id;

    /**
     * Entity constructor
     * @param id Integer - id of entity
     */
    public Entity(int id) {
        this.id = id;
    }

    /**
     * Id getter
     * @return String
     */
    public int getId() {
        return id;
    }

    /**
     * Comparison function that verifies if two objects of type Entity have the same id
     * @param o Object
     * @return true if they have the same id, false if classes do not correspond or they do but have different ids
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    /**
     * Hashcode function
     * @return String
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Forms a string out of an entity
     * @return String
     */
    @Override
    public String toString() {
        return "Entity{" + "id='" + id + '\'' + '}';
    }
}

