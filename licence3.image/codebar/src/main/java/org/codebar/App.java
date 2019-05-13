package org.codebar;

import java.util.ArrayList;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.img.ImgView;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.real.DoubleType;

@Plugin(type=Command.class, name="TestPlugin", menuPath="Plugins>Projet>Test")
public class App<T> implements Command {
	

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<DoubleType> outImage1;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<IntType> outImage2;

	@Parameter
	OpService ops;
	
	@Parameter(type = ItemIO.INPUT, required = true)
	ImgPlus<DoubleType> inputImage;

	@Parameter(type = ItemIO.OUTPUT)
	ImgPlus<DoubleType> outImage;
	
	@Override
	public void run() {
		/*
		System.out.println("Demarrage du plugin");
		
		String code = LiseurPixels.getCodeBinaire(new Binarizator().binarize(inputImage));
		code = Decode.decode(code);
		
		
		System.out.println(code);
		*/
		
		ArrayList<ImgPlus<DoubleType>> cropList = new ArrayList<>();
		

		
		cropList=Detecteur.crop(inputImage);
		
		outImage1 = cropList.get(0);
		
		outImage2= Projection.project(cropList.get(0));
		
	}

}
