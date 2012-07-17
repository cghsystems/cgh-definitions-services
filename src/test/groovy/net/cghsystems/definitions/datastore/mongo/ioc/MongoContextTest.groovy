package net.cghsystems.definitions.datastore.mongo.ioc

import static org.junit.Assert.*

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource

@Configuration
@ImportResource(["classpath:test-definitions-mongo-environment-context.xml",
    "classpath:definitions-mongo-repository-context.xml"])
class MongoContextTest  {
}
