package org.codebar;

import ij.IJ;
import ij.WindowManager;
import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.real.DoubleType;

public class Rotator {

		/**
		 * Effectue une rotation de l'image active de 90 degres dans le sens horaire
		 */		
		public static void rotate90(){

			IJ.run("Rotate 90 Degrees Right");
//			ouff
//			long[] dimso = new long[inputImage.numDimensions()];
//			inputImage.dimensions(dimso);
//			
//			long[] dimsn = new long[inputImage.numDimensions()];
//			ImgPlus<DoubleType> imgOut = ImgPlus.wrap(ArrayImgs.doubles(new long[] {dimso[1],dimso[0]}));
//
//			
//			RandomAccess<DoubleType> outputCursor = temp.randomAccess();
//
//
//			
//			RandomAccess<T> inputImageCursor = inputImage.randomAccess();
//			RandomAccess<IntType> outputCursor = projection.randomAccess();

		}
		/**
		 * Effectue une rotation de l'image active de 45 degres dans le sens horaire
		 */		
		public static void rotate45() {
			IJ.run("Rotate... ", "angle=45 grid=1 interpolation=None enlarge");
		}
		/**
		 * Effectue une rotation de l'image active d'1 degre dans le sens horaire
		 */		
		public static void rotate1cw() {
			IJ.run("Rotate... ", "angle=1 grid=1 interpolation=None enlarge");
		}
}
