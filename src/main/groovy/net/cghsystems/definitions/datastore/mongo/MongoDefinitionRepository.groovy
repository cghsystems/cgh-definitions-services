package net.cghsystems.definitions.datastore.mongo


import net.cghsystems.definitions.model.Definition

import org.springframework.data.mongodb.repository.MongoRepository

interface MongoDefinitionRepository extends MongoRepository<Definition, String>  {

    Definition findById(String id)

    List<Definition> findByDefinitionCategoryId(Long id)
}
