package net.cghsystems.definitions.web.ioc

import static org.junit.Assert.*
import net.cghsystems.definitions.datastore.mongo.ioc.MongoContextTest

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportResource
import org.springframework.test.context.ActiveProfiles


@Configuration
@Import(MongoContextTest)
@ImportResource("classpath:definitions-si-context.xml")
@ActiveProfiles
class DefinitionsControllerApplicationContextTest {
}
