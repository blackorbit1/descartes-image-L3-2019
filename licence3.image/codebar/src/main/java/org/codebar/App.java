package org.codebar;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;

@Plugin(type=Command.class, name="TestPlugin", menuPath="Plugins>Projet>Test")
public class App<T> implements Command {
	
	@Parameter(type = ItemIO.INPUT, required = true)
	ImgPlus<T> inputImage;

	@Override
	public void run() {
		System.out.println("Demarrage du plugin");
		
		String code = LiseurPixels.getCodeBinaire(new Binarizator().binarize(inputImage));
		code = Decode.decode(code);
		
		System.out.println(code);
		
	}

}
