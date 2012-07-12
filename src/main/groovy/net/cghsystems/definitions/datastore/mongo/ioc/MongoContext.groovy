package net.cghsystems.definitions.datastore.mongo.ioc

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource


@Configuration
@ImportResource("classpath:mongo-application-context.xml")
class MongoContext {
}
