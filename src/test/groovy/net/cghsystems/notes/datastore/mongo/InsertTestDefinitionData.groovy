package net.cghsystems.notes.datastore.mongo

import net.cghsystems.notes.model.Definition

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

/**
 * Creates the test {@link Definition} test data is created in the underlying instance of Mongo 
 * that the *integration test* is to be run against.
 * 
 * @author chris
 *
 */
class InsertTestDefinitionData extends DependencyInjectionTestExecutionListener {

    @Autowired
    private MongoOperations mongo

    final static DEF = new Definition(id: "InsertTestDefinitionData-1",
    name: "test-name",
    definition: "test-def",
    description: "test-desc",
    definitionCategoryId: "1")

    final static DEF_2 = new Definition(id: "InsertTestDefinitionData-2",
    name: "test-name-2",
    definition: "test-def-2",
    description: "test-desc-2",
    definitionCategoryId: "1")


    /* (non-Javadoc)
     * @see org.springframework.test.context.support.DependencyInjectionTestExecutionListener#injectDependencies(org.springframework.test.context.TestContext)
     */
    @Override
    protected void injectDependencies(TestContext testContext) throws Exception {
        super.injectDependencies(testContext)
        mongo = testContext.getApplicationContext().getBean(MongoOperations)
    }

    /* (non-Javadoc)
     * @see org.springframework.test.context.support.DependencyInjectionTestExecutionListener#beforeTestMethod(org.springframework.test.context.TestContext)
     */
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {

        super.beforeTestMethod(testContext)

        if(mongo.collectionExists(Definition)) {
            mongo.dropCollection(Definition)
        }
        mongo.createCollection(Definition)

        mongo.save(DEF)
        mongo.save(DEF_2)
    }
}
