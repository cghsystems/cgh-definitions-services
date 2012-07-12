package net.cghsystems.notes.datastore.mongo

import static org.junit.Assert.*
import net.cghsystems.notes.datastore.mongo.ioc.MongoContext

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@ContextConfiguration(classes = [MongoContext])
@RunWith(SpringJUnit4ClassRunner)
@TestExecutionListeners(InsertTestDefinitionData)
class MongoDefinitionRepositoryTest {

    @Autowired
    MongoDefinitionRepository unit

    /**
     * Given a definition exists in the db 
     * When findById call with it's id 
     * Then should return expected {@link Definition} 
     */
    @Test
    void findById() {
        def expected = InsertTestDefinitionData.DEF
        def actual =  unit.findById(expected.id)
        assert actual == expected : "Expecting matching definitions"
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
