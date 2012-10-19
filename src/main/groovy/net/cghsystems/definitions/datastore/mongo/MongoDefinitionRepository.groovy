package net.cghsystems.definitions.datastore.mongo


import net.cghsystems.definitions.model.Definition

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * The Spring-Data {@link Repository} definition that allows CRUD operations to be performed 
 * on a MongoDB {@link Definition} collection. Custom query methods are defined here.
 * 
 * @author chris
 *
 */
@Repository
interface MongoDefinitionRepository extends MongoRepository<Definition, String>  {

    /**
     * @param id of the {@link Definition} to search the repository for. Uses the Spring data naming 
     * convention to create the query
     * 
     * @return the Discovered {@link Definition} or null if one is not found
     */
    Definition findById(String id)

    List<Definition> findByDefinitionCategoryId(Long id)
}
