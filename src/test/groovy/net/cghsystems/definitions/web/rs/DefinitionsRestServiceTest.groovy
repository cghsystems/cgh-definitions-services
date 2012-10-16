package net.cghsystems.definitions.web.rs

import net.cghsystems.definitions.datastore.mongo.InsertTestDefinitionData
import net.cghsystems.definitions.datastore.mongo.MongoDefinitionRepository
import net.cghsystems.definitions.model.Definition
import net.cghsystems.definitions.web.ioc.DefinitionsControllerApplicationContext
import org.codehaus.jackson.map.ObjectMapper
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.server.MockMvc
import org.springframework.test.web.server.ResultMatcher
import org.springframework.test.web.server.setup.MockMvcBuilders

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status

@ContextConfiguration("classpath:META-INF/spring/definitions-mongo-context.xml")
@RunWith(SpringJUnit4ClassRunner)
@TestExecutionListeners(InsertTestDefinitionData)
@ActiveProfiles("dev")
class DefinitionsRestServiceTest {

    @Autowired
    MongoDefinitionRepository mongo

    static MockMvc mvc

    @BeforeClass
    static void before() {
        mvc = MockMvcBuilders.annotationConfigSetup(DefinitionsControllerApplicationContext)
                .activateProfiles("dev")
                .build()
    }

    @Test
    void shouldGetExistingDefinitionWithGetRequest() {

        final expected = new Definition(id: "DefinitionsRestServiceTest-shouldCreateDefinitionWithGetRequest",
                name: "shouldCreateDefinitionWithGetRequest-name",
                definition: "shouldCreateDefinitionWithGetRequest-def",
                description: "shouldCreateDefinitionWithGetRequest-desc",
                definitionCategoryId: "1")

        final createURL = "/create/id/${expected.id}/name/${expected.name}/definition/${expected.definition}/description/${expected.description}/cat/49"
        mvc.perform(get(createURL)).andExpect(status().isOk())

        final matcher = {
            final actual = new ObjectMapper().readValue(it.getResponse().getContentAsString(), Definition)
            assert actual == expected: "Expected matching definitions"
        }

        final findURL = "/find/id/${expected.id}"
        mvc.perform(get(findURL))
                .andExpect([match: matcher] as ResultMatcher)
                .andExpect(status().isOk())
    }

    @Test
    void shouldHandleRequestForInvalidDefinition() {

        final expected = new Definition(id: "DefinitionsRestServiceTest-shouldCreateDefinitionWithGetRequest",
                name: "shouldCreateDefinitionWithGetRequest-name",
                definition: "shouldCreateDefinitionWithGetRequest-def",
                description: "shouldCreateDefinitionWithGetRequest-desc",
                definitionCategoryId: "1")

        final createURL = "/create/id/random/name/${expected.name}/definition/${expected.definition}/description/${expected.description}/cat/49"
        mvc.perform(get(createURL))

        final findURL = "/find/id/${expected.id}"
        mvc.perform(get(findURL))
                .andExpect(status().isOk())
                .andExpect([match: { it == null }] as ResultMatcher)
    }


    @Test
    void shouldDeleteDefinition() {
        final deleteURL = "/delete/id/${InsertTestDefinitionData.DEF.id}"
        assert mongo.exists(InsertTestDefinitionData.DEF.id) == true: "Definition should exist here"
        mvc.perform(delete(deleteURL))
                .andExpect(status().isOk())
        assert mongo.exists(InsertTestDefinitionData.DEF.id) == false: "Definition should have been deleted"
    }

    @Test
    void shouldDoNothingIfDeleteAttemptsToDelteMissingDefinition() {
        final id = "Random"
        final deleteURL = "/delete/id/${id}"
        assert mongo.exists(id) == false: "Definition should not exist here"
        mvc.perform(delete(deleteURL))
                .andExpect(status().isOk())
        assert mongo.exists(id) == false: " deletion should have been handled silently"
    }
}
