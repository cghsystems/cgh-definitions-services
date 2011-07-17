package net.cghsystems.notes;

import net.cghsystems.notes.cli.DefinitionsCLI;
import net.cghsystems.notes.ui.DefinitionsGUI;


public class Main {
	
	static main(args) {
		if(args[0] == "gui") {
			DefinitionsGUI.main(args)
		}else {
			DefinitionsCLI.main(args)
		}
	}
}
