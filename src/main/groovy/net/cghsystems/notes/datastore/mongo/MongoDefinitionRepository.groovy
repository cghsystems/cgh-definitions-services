package net.cghsystems.notes.datastore.mongo

import net.cghsystems.notes.model.Definition

import org.springframework.data.repository.Repository


interface MongoDefinitionRepository extends Repository<Definition, Serializable>  {


    Definition findById(String id)

    List<Definition> findByDefinitionCategoryId(Long id)
}
