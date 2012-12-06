package net.cghsystems.definitions.datastore.mongo


import net.cghsystems.definitions.domain.Definition

import org.springframework.data.mongodb.repository.MongoRepository

import org.springframework.stereotype.Repository

/**
 * The Spring-Data {@link Repository} definition that allows CRUD operations to be performed 
 * on a MongoDB {@link Definition} collection. Custom query methods are defined here.
 * 
 * @author chris
 *
 */
@Repository
interface DefinitionRepository extends MongoRepository<Definition, String>  {

    List<Definition> findByDefinitionCategoryId(Long id)

    /**
     * Searches for a Definition by name
     * @param name to search for
     * @return A Definition matched by name or null if none can be located.
     */
    Definition findByName(String name)

}
