package net.cghsystems.definitions.web.ioc

import net.cghsystems.notes.datastore.mongo.ioc.MongoContext

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportResource
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@Configuration
@Import(MongoContext)
@ImportResource("classpath:definitions-si-context.xml")
@EnableWebMvc
class DefinitionsControllerApplicationContext {
}
