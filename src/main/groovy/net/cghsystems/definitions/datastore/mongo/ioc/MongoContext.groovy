package net.cghsystems.definitions.datastore.mongo.ioc

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource


@Configuration
@ImportResource(["classpath:definitions-mongo-environment-context.xml",
    "classpath:definitions-mongo-repository-context.xml"])
class MongoContext {
}
