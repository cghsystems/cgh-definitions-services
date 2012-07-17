package net.cghsystems.definitions.datastore.mongo

import static org.junit.Assert.*
import net.cghsystems.definitions.datastore.mongo.ioc.MongoContextTest
import net.cghsystems.definitions.model.Definition

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@ContextConfiguration(classes = [MongoContextTest])
@RunWith(SpringJUnit4ClassRunner)
@TestExecutionListeners(InsertTestDefinitionData)
class MongoDefinitionRepositoryTest {

    @Autowired
    MongoDefinitionRepository unit

    /**
     * Given a definition exists in the db 
     * When {@link MongoDefinitionRepository#findById(String)} call with it's id 
     * Then should return expected {@link Definition} 
     */
    @Test
    void findById() {
        def expected = InsertTestDefinitionData.DEF
        def actual =  unit.findById(expected.id)
        assert actual == expected : "Expecting matching definitions"
    }

    /**
     * Given a {@link Definition} 
     * When {@link MongoDefinitionRepository#save(net.cghsystems.definitions.model.Definition)} 
     * Then repo should contain expected {@link Definition}
     */
    @Test
    void save() {
        def expected = new Definition(id: "MongoDefinitionRepositoryTest-create",
                name: "create",
                definition: "test-def",
                description: "test-desc",
                definitionCategoryId: "1")
        unit.save(expected)
        def actual =  unit.findById(expected.id)
        assert actual == expected : "Expecting matching definitions"
    }

    @Test
    void saveTwiceShouldNotUpdate() {
        def original = new Definition(id: "MongoDefinitionRepositoryTest-create",
                name: "create",
                definition: "test-def",
                description: "test-desc",
                definitionCategoryId: "1")
        unit.save(original)
        def actual =  unit.findById(original.id)
        assert actual == original : "Expecting matching definitions"

        def ediited = new Definition(id: "MongoDefinitionRepositoryTest-create",
                name: "create",
                definition: "somethingchanged",
                description: "test-desc",
                definitionCategoryId: "1")
        unit.save(original)
        def editedActual =  unit.findById(original.id)
        assert editedActual == original : "Expecting different definitions"
    }



    @Test
    void cannotFindById() {
        assert null == unit.findById("1")
    }

    /**
     * Given a {@link Definition} exists in the db
     * When findByDefinitionCategoryId with it's id
     * Then should return expected {@link Definition}
     */
    @Test
    void findByDefinitionCategoryId() {
        def expected = InsertTestDefinitionData.DEF
        def actual =  unit.findByDefinitionCategoryId(expected.definitionCategoryId)
        assert actual.containsAll(expected, InsertTestDefinitionData.DEF_2) : "Expecting matching definitions"
    }
}
