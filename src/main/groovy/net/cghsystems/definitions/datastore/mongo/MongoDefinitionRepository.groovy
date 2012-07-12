package net.cghsystems.definitions.datastore.mongo


import net.cghsystems.definitions.model.Definition

import org.springframework.data.repository.Repository


interface MongoDefinitionRepository extends Repository<Definition, Serializable>  {

    Definition findById(String id)

    List<Definition> findByDefinitionCategoryId(Long id)

    void save(Definition definition)
}
