package org.codebar;

import java.util.ArrayList;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.ImagePlus;
import ij.process.ImageConverter;
import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.img.ImagePlusAdapter;
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
		
		//Test de bon fonctionnement
		Rotator.rotate90();
		Rotator.rotate45();
		Rotator.rotate1cw();
		
		//On definie une ImagePlus avec l'image actuellement ouverte
		ImagePlus temp = ij.IJ.getImage();
		//On cree un ImageConverter, initialise avec cette ImagePlus
		ImageConverter converter = new ImageConverter(temp);
		//On convertit ImagePlus en 8-bit avec le ImageConverter
		converter.convertToGray8();
		//On utilise ImagePlusAdapter pour transformer ImagePlus en ImgPlus
		inputImage = ImagePlusAdapter.wrapImgPlus(temp);
		//On wrap le ImgPlus sur lui-meme pour enforcer le type <DoubleType>, sinon ca plante
		inputImage = new ImgPlus<DoubleType>(ImgView.wrap(inputImage, null), "img");

		
		cropList=Detecteur.crop(inputImage);
		
		
		outImage1 = cropList.get(0);
		
		outImage2= Projection.project(cropList.get(0));
		
		StringBuilder string = new StringBuilder();
		string.reverse();
		
	}

}
