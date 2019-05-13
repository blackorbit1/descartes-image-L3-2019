package org.codebar;

import java.util.Arrays;

import org.scijava.ItemIO;
import org.scijava.plugin.Parameter;

import ij.ImagePlus;
import ij.process.ImageConverter;
import net.imagej.ImgPlus;
import net.imagej.ops.OpService;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.ImgView;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.DoubleType;

public class Detector<T extends RealType<T>> {



//	@Parameter
//	ImgPlus<T> inputImage;
	



	
	public ImgPlus<DoubleType> detect(ImgPlus<T> inputImage, OpService ops) {
		
		
		String kernelStringHorizontal = "1 2 1;0 0 0;-1 -2 -1";

		String kernelStringVertical = "1 0 -1;2 0 -2;1 0 -1";

		double[][] vkValues = Arrays.stream(kernelStringVertical.split(";"))
				.map(s -> Arrays.stream(s.split(" +")).mapToDouble(Double::parseDouble).toArray()).toArray(double[][]::new);
		double[][] hkValues = Arrays.stream(kernelStringHorizontal.split(";"))
				.map(s -> Arrays.stream(s.split(" +")).mapToDouble(Double::parseDouble).toArray()).toArray(double[][]::new);

		System.out.println(Arrays.deepToString(vkValues));
		System.out.println(Arrays.deepToString(hkValues));

		RandomAccessibleInterval<DoubleType> vKernel = ops.create().kernel(vkValues, new DoubleType(0d));
		RandomAccessibleInterval<DoubleType> hKernel = ops.create().kernel(hkValues, new DoubleType(0d));

		@SuppressWarnings({ "unchecked" })
		RandomAccessibleInterval<DoubleType> verticalOutv = ops.convert()
		.float64((IterableInterval<DoubleType>) ops.filter().convolve(inputImage, vKernel));
		
		@SuppressWarnings({ "unchecked" })
		RandomAccessibleInterval<DoubleType> horizontalOutv = ops.convert()
		.float64((IterableInterval<DoubleType>) ops.filter().convolve(inputImage, hKernel));

		ImgPlus<DoubleType> verticalOut;
		ImgPlus<DoubleType> horizontalOut;
		



		verticalOut = new ImgPlus<DoubleType>(ImgView.wrap(verticalOutv, null), "img");
		horizontalOut = new ImgPlus<DoubleType>(ImgView.wrap(horizontalOutv, null), "img");
		
		long[] imageDimensions = new long[inputImage.numDimensions()];
		inputImage.dimensions(imageDimensions);

		ImgPlus<DoubleType> resultOut;
		resultOut = ImgPlus.wrap(ArrayImgs.doubles(imageDimensions));
		
		RandomAccess<DoubleType> cursorV = verticalOut.randomAccess();
		RandomAccess<DoubleType> cursorH = horizontalOut.randomAccess();
		RandomAccess<DoubleType> cursorR = resultOut.randomAccess();

		int[] position = new int[inputImage.numDimensions()];
		
		Double x;
		Double y;
		Double z;
		
		for(int i=1;i<imageDimensions[0];i++) {
			position[0]=i;
			for(int j=1;j<imageDimensions[1];j++) {
				position[1]=j;
				cursorR.setPosition(position);
				cursorV.setPosition(position);
				cursorH.setPosition(position);
				
//				cursorR.get().setReal(Math.atan(cursorV.get().getRealDouble()/cursorH.get().getRealDouble()));
				cursorR.get().setReal(cursorH.get().getRealDouble()-cursorV.get().getRealDouble());
				
			
			}
		}	

		// Save result as output
		return verticalOut;
	}
}
