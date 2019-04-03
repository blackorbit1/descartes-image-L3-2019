package org.codebar;

import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

@Plugin(type=Command.class, name="TestPlugin", menuPath="Plugins>Projet>Test")
public class App implements Command {

	@Override
	public void run() {
		System.out.println("OK");
	}

}
