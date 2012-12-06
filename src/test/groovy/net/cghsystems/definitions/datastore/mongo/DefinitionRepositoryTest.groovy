package net.cghsystems.definitions.datastore.mongo

import net.cghsystems.definitions.domain.Definition
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@ContextConfiguration("classpath:META-INF/spring/definitions-mongo-context.xml")
@RunWith(SpringJUnit4ClassRunner)
@TestExecutionListeners(InsertTestDefinitionData)
@ActiveProfiles("dev")
class DefinitionRepositoryTest {

    @Autowired
    DefinitionRepository unit

    /**
     * Given a definition exists in the db 
     * When {@link DefinitionRepository#findOne(String)} call with it's id
     * Then should return expected {@link Definition}
     */
    @Test
    void findOne() {
        final expected = InsertTestDefinitionData.DEF
        final actual = unit.findOne(expected.id)
        assert actual == expected: "Expecting matching definitions"
    }

    @Test
    void findByName() {
        final expected = InsertTestDefinitionData.DEF
        final actual = unit.findByName(expected.name)
        assert actual == expected: "Expecting matching definitions"
    }

    @Test
    void cannotFindByName() {
        final actual = unit.findByName("Something Random")
        assert actual == null: "Expecting matching definitions"
    }

    /**
     * Given a {@link Definition}
     * When {@link DefinitionRepository#save(net.cghsystems.definitions.model.Definition)}
     * Then repo should contain expected {@link Definition}
     */
    @Test
    void save() {
        final expected = new Definition(id: "1",
                name: "create",
                definition: "test-def",
                description: "test-desc",
                definitionCategoryId: 1L)
        unit.save(expected)
        final actual = unit.findOne(expected.id)
        assert actual == expected: "Expecting matching definitions"
    }

    @Test
    void saveWithoutDefinitionIdShouldCreateId() {
        final expected = new Definition(name: "create",
                definition: "test-def",
                description: "test-desc",
                definitionCategoryId: 1L)
        final actual = unit.save(expected)
        assert actual.id  : "Was expecting id to generate"

        final expected2 = new Definition(name: "different",
                definition: "test-def",
                description: "test-desc",
                definitionCategoryId: 1L)

        final actual2 = unit.save(expected2)
        assert actual.id != actual2.id
    }

    @Test
    void cannotFindById() {
        assert null == unit.findOne("random")
    }

    /**
     * Given a {@link Definition} exists in the db
     * When findByDefinitionCategoryId with it's id
     * Then should return expected {@link Definition}
     */
    @Test
    void findByDefinitionCategoryId() {
        final expected = InsertTestDefinitionData.DEF
        final actual = unit.findByDefinitionCategoryId(expected.definitionCategoryId)
        assert actual.containsAll(expected, InsertTestDefinitionData.DEF_2): "Expecting matching definitions"
    }
}
