package net.cghsystems.definitions.web.rs

import net.cghsystems.definitions.datastore.mongo.DefinitionRepository
import net.cghsystems.definitions.datastore.mongo.InsertTestDefinitionCategoryData
import net.cghsystems.definitions.datastore.mongo.InsertTestDefinitionData
import net.cghsystems.definitions.domain.Definition
import net.cghsystems.definitions.domain.DefinitionCategory
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
import org.springframework.test.web.server.ResultHandler
import org.springframework.test.web.server.ResultMatcher
import org.springframework.test.web.server.setup.MockMvcBuilders

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status
import org.codehaus.jackson.map.type.TypeFactory

@ContextConfiguration("classpath:META-INF/spring/definitions-mongo-context.xml")
@RunWith(SpringJUnit4ClassRunner)
@TestExecutionListeners([InsertTestDefinitionData, InsertTestDefinitionCategoryData])
@ActiveProfiles("dev")
class DefinitionsRestServiceTest {

    @Autowired
    DefinitionRepository mongo

    static MockMvc mvc

    @BeforeClass
    static void before() {
        mvc = MockMvcBuilders.annotationConfigSetup(DefinitionsControllerApplicationContext)
                .activateProfiles("dev")
                .build()
    }


    @Test
    void shouldPing() {
        final url = "/ping/message/mesage-from-unit-test"
        mvc.perform(get(url)).andExpect(status().isOk())
    }

    @Test
    void shouldGetExistingDefinitionWithGetRequest() {

        def expected = new Definition(
                name: "shouldCreateDefinitionWithGetRequest-name",
                definition: "shouldCreateDefinitionWithGetRequest-def",
                description: "shouldCreateDefinitionWithGetRequest-desc",
                definitionCategoryId: "1")

        final getCreatedIdHandler = {
            expected = new ObjectMapper().readValue(it.getResponse().getContentAsString(), Definition)
        }

        final createURL =
            "/create/name/${expected.name}/definition/${expected.definition}/description/${expected.description}/cat/49"

        mvc.perform(get(createURL)).andExpect(status().isOk()).andDo([handle: getCreatedIdHandler] as ResultHandler)

        final matcher = {
            final actual = new ObjectMapper().readValue(it.getResponse().getContentAsString(), Definition)
            assert actual == expected: "Expected matching categories"
        }

        final findURL = "/find/id/${expected.id}"
        mvc.perform(get(findURL))
                .andExpect([match: matcher] as ResultMatcher)
                .andExpect(status().isOk())
    }

    @Test
    void shouldHandleRequestForDefinitionWithNoId() {

        final expected = new Definition(
                name: "shouldHandleRequestForDefinitionWithNoId-name",
                definition: "shouldCreateDefinitionWithGetRequest-def",
                description: "shouldCreateDefinitionWithGetRequest-desc",
                definitionCategoryId: 1)

        final createURL = "/create/name/${expected.name}/definition/${expected.definition}/description/${expected.description}/cat/${expected.definitionCategoryId}"

        final matcher = {
            final actual = new ObjectMapper().readValue(it.getResponse().getContentAsString(), Definition)
            println actual.id
            assert actual.id: "Was expecting an id to have been generated but was null"
        }

        mvc.perform(get(createURL))
                .andExpect(status().isOk())
                .andExpect([match: matcher] as ResultMatcher)
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

    //May need a new test class for below
    @Test
    void shouldFindAllCategories() {

        final resultMatcher = {
            final type = TypeFactory.defaultInstance().constructParametricType(Collection.class, DefinitionCategory)
            final actual = new ObjectMapper().readValue(it.getResponse().getContentAsString(), type)
            assert actual == [InsertTestDefinitionCategoryData.CAT, InsertTestDefinitionCategoryData.CAT_2]
        }

        final findCategoriesURL = "/category/findall"
        mvc.perform(get(findCategoriesURL)).andExpect(status().isOk())
                .andExpect([match: resultMatcher] as ResultMatcher)
    }
}
