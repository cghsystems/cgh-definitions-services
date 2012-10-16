package net.cghsystems.definitions.datastore.mongo

import net.cghsystems.definitions.model.Definition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

/**
 * A spring {@link TestExecutionListeners} that creates the test {@link Definition} test data 
 * in the underlying instance of Mongo that *integration tests* are to be run against. The data is deleted after test
 * method as MongoDB in a non transactional database.
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

    private definitions = [DEF,DEF_2]

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
    void beforeTestMethod(TestContext testContext) throws Exception {

        super.beforeTestMethod(testContext)

        removeDefinitions()
        createCollectionIfRequired();

        definitions.each { mongo.save(it) }

    }

    void createCollectionIfRequired() {
        if (!mongo.collectionExists(Definition)) {
            mongo.createCollection(Definition)
        }
    }

    @Override
    void afterTestMethod(TestContext testContext) {
        super.afterTestMethod(testContext)    //To change body of overridden methods use File | Settings | File Templates.
        removeDefinitions()
    }

    /**  Does exactly what is says. Drops the Definitions collection from the definition db after each test **/
    private void removeDefinitions() {
        definitions.each { mongo.remove(it) }
    }
}
