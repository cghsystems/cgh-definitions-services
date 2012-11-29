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
interface MongoDefinitionRepository extends MongoRepository<Definition, String>  {

    List<Definition> findByDefinitionCategoryId(Long id)
}
