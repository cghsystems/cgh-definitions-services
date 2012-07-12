/**
 *
 */
package net.cghsystems.notes.xml

import net.cghsystems.notes.datastore.DefinitionRepository
import net.cghsystems.notes.model.Definition




class SingleXMLDefinitionStore implements DefinitionRepository {

    private static final String sourceDir = "C:/dev/bin/groovy-scripts/"

    private static final sources = [
        common:"${sourceDir}/common-definitions.xml",
        dotnet:"${sourceDir}/dotnet-definitions.xml",
        ubs:"${sourceDir}/ubs-definitions.xml"
    ]

    private storeSource = sources["common"]

    @Override
    def getDefinitionStoreSourceKeys() {
        sources.keySet()
    }

    @Override
    void updateCurrentStoreSource(key) {
        storeSource = sources[key]
    }

    @Override
    def getSize() {
        def defs = new XmlParser().parse(storeSource)
        defs.defin.size()
    }

    @Override
    def query(name) {
        def defs = new XmlParser().parse(storeSource)
        return defs.defin.findAll { it.@name =~ "(?i)${name.trim()}:*" } //Add reg ex to ignore case
    }

    @Override
    void add(name,definition,description) {
        def defs = new XmlParser().parse(storeSource)
        appendNodeAndWriteDefs(defs, name, definition, description)
    }

    @Override
    void edit(id,name,definition,description) {
        def defs = new XmlParser().parse(storeSource)
        def results = defs.defin.findAll{ it.@id == id }
        if(results.size() > 1) {
            println "Ambiguous Definition - Please edit $storeSource} manualy"
        }else {
            removeNode(defs,results[0])
            appendNodeAndWriteDefs(defs, name, definition, description)
            println "Definition: ${name} edited"
        }
    }

    @Override
    def delete(id) {
        def defs = new XmlParser().parse(storeSource)
        def results = defs.defin.findAll{ it.'@id' == id }

        if(results.size() > 1) {
            "Ambiguous Definition - Please delete $storeSource} manualy"
        }else {
            removeNodeFromDefintionsAndWriteResult(defs, results, id)
        }
    }

    @Override
    def getDefinitionsForQuery(name) {
        def found = query(name)
        found.collect {
            new Definition(id: it.'@id', name: it.'@name', definition: it.'@definition', description: it.text() )
        }
    }

    private removeNodeFromDefintionsAndWriteResult(Node defs, List results, id) {
        try {
            removeNode(defs, results[0])
            writeDefs(defs)
            "${results[0]} with ID: ${id} deleted"
        }catch(e) {
            "Could not delete name"
        }
    }

    private void removeNode(defs,node) {
        defs.remove(node)
    }

    private void appendNodeAndWriteDefs(defs, name, definition, description) {
        defs.appendNode("defin", [id:UUID.randomUUID(), name:name, definition:definition], description)
        writeDefs(defs)
    }

    private void writeDefs(defs) {
        new XmlNodePrinter(new PrintWriter(storeSource)).print(defs)
    }
}
