package net.cghsystems.definitions.datastore.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import net.cghsystems.definitions.domain.DefinitionCategory

/**
 * Spring data repo in the the DefinitionCategory
 *
 * @author: chris
 * @date: 06/12/2012
 */
interface DefinitionCategoryRepository extends MongoRepository<DefinitionCategory, String> {

}
