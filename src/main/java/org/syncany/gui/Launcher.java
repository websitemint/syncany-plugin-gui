package org.syncany.gui;

import org.syncany.cli.GuiCommand;

public class Launcher {
	public static void main(String[] args) throws Exception {
		System.exit(new GuiCommand().execute(args));
		
		/*Logging.init();
		
		GuiOperationOptions guiOperationOptions = new GuiOperationOptions();
		guiOperationOptions.setTrayType(TrayIconType.PYTHON);
		
		new GuiOperation(guiOperationOptions).execute();*/
	}	
}