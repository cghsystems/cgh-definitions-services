/**
 * 
 */
package net.cghsystems.notes.cli;

import net.cghsystems.notes.SingleXMLDefinitionStore;


/**
 * 
 */
public class DefinitionsCLI {
	
	static  main(args) {
		//replace with CliBuilder
		def cmd = args[0]
		def defs = new DefinitionsCLI();
		
		switch(cmd) {
			case "-add":
				defs.add(args[1], args[2], args[3])
				break;
			case "-delete":
				defs.deleteAndPrintResults(args[1])
				break;
			case "-edit":
				defs.edit(args[1], args[2], args[3])
				break;
			case "-help":
				defs.printUsage()
				break;
			case "-ls":
				defs.printAll()
				break;
			case "-size":
				defs.printSize()
				break;
			default:
				defs.queryAndPrintResults(cmd)
		}
	}
	
	void printAll() {
		def defs = new SingleXMLDefinitionStore().getDefinitionsForQuery("")
		defs.defin.each { printResult it }
	}
	
	void queryAndPrintResults(name) {
		def found = new SingleXMLDefinitionStore().query(name)
		found.each { printResult it }
	}
	
	void printSize() {
		println "Number of definitions: ${new SingleXMLDefinitionStore().getSize()}"
	}
	
	void add(name,definition,description) {
		new SingleXMLDefinitionStore().add(name,definition,description)
	}
	
	void deleteAndPrintResults(name) {
		println new SingleXMLDefinitionStore().delete(name)
	}
	
	void edit(name,definition,description) {
		new SingleXMLDefinitionStore().edit(name,definition,description)
	}
	
	void printUsage() {
		def usage =
		"""Definitions usage
-----------------
-add "definitions name" "definition" "definition-description"
-delete "definitions name"
-edit "definitions name" "definition" "definition-description"
-help prints usage for the application
-ls prints all definitions
-size prints the number definitions
query - The definition to query for
              """
		
		println usage
	}
	
	private void printResult(result) {
		def res =
		"""name: ${result.'@name'}
definition: ${result.'@definition'}
description: ${result.text()}
"""
		println res
	}
}
