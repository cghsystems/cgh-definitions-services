package net.cghsystems.definitions.web.ioc


import net.cghsystems.definitions.datastore.mongo.ioc.MongoContext

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportResource


/**
 * Spring MVC core configuration object.
 * 
 * @author chris
 *
 */
@Configuration
@Import(MongoContext)
@ImportResource("classpath:definitions-si-context.xml")
class DefinitionsControllerApplicationContext {
}
