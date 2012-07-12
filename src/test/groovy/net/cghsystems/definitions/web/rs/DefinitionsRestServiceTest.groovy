package net.cghsystems.definitions.web.rs

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get
import net.cghsystems.definitions.datastore.mongo.MongoDefinitionRepository
import net.cghsystems.definitions.datastore.mongo.ioc.MongoContext
import net.cghsystems.definitions.model.Definition
import net.cghsystems.definitions.web.ioc.DefinitionsControllerApplicationContext

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.server.setup.MockMvcBuilders


@ContextConfiguration(classes = [MongoContext])
@RunWith(SpringJUnit4ClassRunner)
class DefinitionsRestServiceTest {

    @Autowired
    MongoDefinitionRepository mongo

    @Test
    void shouldCreateDefinitionWithGetRequest() {

        final expected = new Definition(id: "DefinitionsRestServiceTest-shouldCreateDefinitionWithGetRequest",
                name: "shouldCreateDefinitionWithGetRequest-name",
                definition: "shouldCreateDefinitionWithGetRequest-def",
                description: "shouldCreateDefinitionWithGetRequest-desc",
                definitionCategoryId: "1")

        final url = "/create/id/${expected.id}/name/${expected.name}/definition/${expected.definition}/description/${expected.description}/cat/49"
        MockMvcBuilders.annotationConfigSetup(DefinitionsControllerApplicationContext).build().perform(get(url))

        final actual = mongo.findById(expected.id)
        assert actual ==  expected : "Expected matching definitions"
    }

    @Test
    @Ignore
    void shouldCreateDefinitionWithPostRequest() {
        throw null
    }

    @Test
    @Ignore
    void shouldDeleteDefinition() {
        throw null
    }
}
