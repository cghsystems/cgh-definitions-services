/**
 * 
 */
package net.cghsystems.definitions.datastore

interface DefinitionRepository {

    /** names of all of the available stores by key */
    def getDefinitionStoreSourceKeys()

    void updateCurrentStoreSource(key)

    /** collection of definitions objects built from the query parameter */
    def getDefinitionsForQuery(name)

    /** add a definition to the store */
    void add(name,definition,description)

    /** remove a definition from the store */
    def delete(name)

    /** edit a definition on the store */
    void edit(id,name,definition,description)
}
