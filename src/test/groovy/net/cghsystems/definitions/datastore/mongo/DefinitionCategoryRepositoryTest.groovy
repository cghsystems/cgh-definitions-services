package net.cghsystems.definitions.datastore.mongo

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@ContextConfiguration("classpath:META-INF/spring/definitions-mongo-context.xml")
@RunWith(SpringJUnit4ClassRunner)
@TestExecutionListeners(InsertTestDefinitionCategoryData)
@ActiveProfiles("dev")
class DefinitionCategoryRepositoryTest {

    @Autowired
    DefinitionCategoryRepository unit

    @Test
    void shouldFindAllCategories() {
        final expected = [InsertTestDefinitionCategoryData.CAT, InsertTestDefinitionCategoryData.CAT_2]
        assert expected == unit.findAll()
    }
}
