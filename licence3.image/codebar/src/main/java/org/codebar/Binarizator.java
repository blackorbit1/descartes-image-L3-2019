package org.codebar;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.AbstractIntegerType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

public class Binarizator {
	public <T> ImgPlus binarize(ImgPlus<T> inputImage) {
		ImgPlus<UnsignedByteType> mask;
		int threshold = 95;
		
		//DIMENSION EST LE NOMBRE DE COULEURS QUI L'IMAGE A.
		//mask est la image résultat.
		// dimensions holds the size of the input image in x and y
		long[] imageDimensions = new long[inputImage.numDimensions()]; // (?)
		long[] maskDimensions = new long[inputImage.numDimensions()];  // (?)
		inputImage.dimensions(imageDimensions);						   // (?)
		inputImage.dimensions(maskDimensions);						   // (?)
		if(maskDimensions.length > 2)								   // (?)
			maskDimensions[2] = 1;									   // (?)
		
		
		// Creation of the resulting image with the same size as the input image.

		mask = ImgPlus.wrap(ArrayImgs.unsignedBytes(maskDimensions));
		mask.setName(inputImage.getName() + "_Mask");

		// Two random cursor to visit all pixels in the input and output images.
		RandomAccess<T> inputImageCursor = inputImage.randomAccess();
		RandomAccess<UnsignedByteType> maskCursor = mask.randomAccess();

		long[] currentPosition = new long[imageDimensions.length];
		// 1. Parcourir toutes les lignes de l'image
		for (int i = 0; i < imageDimensions[0]; i++) {
			// 2. Parcourir toutes les colonnes de l'image
			for (int j = 0; j < imageDimensions[1]; j++) {
				// 3. affecter la position aux curseurs
				currentPosition[0] = i;
				currentPosition[1] = j;
				inputImageCursor.setPosition(currentPosition);
				maskCursor.setPosition(currentPosition);

				// 4. obtenir intensite de l'image a la position currentPosition
				if (((AbstractIntegerType<UnsignedByteType>) inputImageCursor.get()).getRealDouble() > threshold) {
					// 5. affecter pixel de l'image de sortie
					maskCursor.get().set(255);
				}
			}
		}
		return mask;
	}





}
	

