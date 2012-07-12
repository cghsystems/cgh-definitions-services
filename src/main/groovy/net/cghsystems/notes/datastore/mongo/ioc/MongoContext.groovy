package net.cghsystems.notes.datastore.mongo.ioc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource
import org.springframework.data.mongodb.core.MongoFactoryBean
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate

import com.mongodb.Mongo


@Configuration
@ImportResource("classpath:mongo-application-context.xml")
class MongoContext {


    @Bean
    MongoOperations mongoTemplate(Mongo mongo) {
        new MongoTemplate(mongo, "cgh-notes")
    }

    @Bean
    MongoFactoryBean mongo() {
        new MongoFactoryBean()
    }
}
