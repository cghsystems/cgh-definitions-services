package net.cghsystems.definitions.datastore.mongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.test.context.TestContext
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import net.cghsystems.definitions.domain.DefinitionCategory

/**
 * A spring {@link org.springframework.test.context.TestExecutionListeners} that creates the test {@link DefinitionCategory} test data
 * in the underlying instance of Mongo that *integration tests* are to be run against. The data is deleted after test
 * method as MongoDB in a non transactional database.
 *
 * @author chris
 *
 */
class InsertTestDefinitionCategoryData extends DependencyInjectionTestExecutionListener {

    @Autowired
    private MongoOperations mongo

    final static CAT = new DefinitionCategory(name: "Category 1")

    final static CAT_2 = new DefinitionCategory(name: "Category 2")

    private categories = [CAT, CAT_2]

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

        removeCategories()
        createCollectionIfRequired();

        categories.each {
            mongo.save(it);
        }
    }

    void createCollectionIfRequired() {
        if (!mongo.collectionExists(DefinitionCategory)) {
            mongo.createCollection(DefinitionCategory)
        }
    }

    @Override
    void afterTestMethod(TestContext testContext) {
        super.afterTestMethod(testContext)
        removeCategories()
    }

    /**  Does exactly what is says. Drops the Definitions collection from the definition db after each test **/
    private void removeCategories() {
        categories.each { mongo.remove(it) }
        mongo.dropCollection(DefinitionCategory)
    }
}
