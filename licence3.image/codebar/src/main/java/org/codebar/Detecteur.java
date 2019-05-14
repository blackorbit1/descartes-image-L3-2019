package org.codebar;

import java.util.ArrayList;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;

import ij.IJ;
import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.img.ImgView;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.AbstractIntegerType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.DoubleType;

public class Detecteur {


	public static <DoubleType> ImgPlus<DoubleType> getBarcode(ImgPlus<DoubleType> inputImage) {

		int variations;
		int smallestElement;
		int[] startCoordinate, endCoordinate;

		RandomAccess<DoubleType> inputImageCursor = inputImage.randomAccess();

		long[] dimensions = new long[inputImage.numDimensions()];
		inputImage.dimensions(dimensions);



		for(int i=0;i<dimensions[0];i++) {

		}
		return inputImage;
	}

	public static <T extends RealType> ArrayList<ImgPlus<DoubleType>> crop(ImgPlus<T> inputImage){
		Rotator.rotate90();
		ArrayList<ImgPlus<DoubleType>> list = new ArrayList<>();

		long[] dimensions = new long[inputImage.numDimensions()];
		inputImage.dimensions(dimensions);

		int cropCount = (int) (dimensions[1]/35);

		RandomAccess<T> inputImageCursor = inputImage.randomAccess();

		for(int i=0;i<(cropCount);i++) {


			ImgPlus<DoubleType> temp = ImgPlus.wrap(ArrayImgs.doubles(new long[] {dimensions[0],35}));
			RandomAccess<DoubleType> outputCursor = temp.randomAccess();

			long[] currentPosition = new long[dimensions.length];
			long[] outputPosition = new long[dimensions.length];

			for (int n = 0; n < dimensions[0]; n++) {
				currentPosition[0]=n;
				outputPosition[0]=n;
				for (int j = 0; j < 35; j++) {
					currentPosition[1] = i*35+j;
					outputPosition[1] = j;
	
					inputImageCursor.setPosition(currentPosition);
					outputCursor.setPosition(outputPosition);

					outputCursor.get().setReal(inputImageCursor.get().getRealDouble());
				}
			}

			list.add(temp);



		}

		return list;

	}
}
