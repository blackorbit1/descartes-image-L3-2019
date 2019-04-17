package org.codebar;

import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.IntType;

import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ImgPlus;
import net.imglib2.RandomAccess;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.IntType;

public class LiseurPixels {
	
	public static <T extends RealType<T>> String getCodeBinaire(ImgPlus<T> inputImage) {
		long[] dims = new long[inputImage.numDimensions()];
		inputImage.dimensions(dims);
		RandomAccess<T> inputImageCursor = inputImage.randomAccess();

		long[] posInputImage = new long[inputImage.numDimensions()];
		posInputImage[1] = inputImage.dimension(1) / 2;
		
		int [] tab_bits = new int[95];
		int longueur_bit = (int) (inputImage.dimension(0) / 95);
		int longueur_changement = longueur_bit / 3;
		
		boolean debut = true;
		boolean dernier_noir = false;
		int nb_bit = 0;
		int nb_pixels = 0;
		int decompte_parcours_bordure = 0;
		int nb_pixels_last_change = 0;
		
		System.out.println("longueur_bit : " + longueur_bit);
		System.out.println("longueur_changement : " + longueur_changement);

		for (int i = 0; i < inputImage.dimension(0) && nb_bit < 95; i++) {
			nb_pixels++;
			posInputImage[0] = i;
			inputImageCursor.setPosition(posInputImage);
			boolean changement = false;
			decompte_parcours_bordure--;
			
			if(i > 1 && i < (inputImage.dimension(0) - 1)) {
				
				
				posInputImage[0] = i;
				inputImageCursor.setPosition(posInputImage);
				int sum_gauche = (int) inputImageCursor.get().getRealDouble();
				
				posInputImage[0] = i+1;
				inputImageCursor.setPosition(posInputImage);
				int sum_droite = (int) inputImageCursor.get().getRealDouble();
				
				/*
				 * Pourrait servir si y a des nuances mais ce n'est pas le cas
				 * 
				int sum_gauche = 0;
				for(int i_gauche = i; i_gauche < i-longueur_changement; i_gauche++) {
					posInputImage[0] = i_gauche;
					inputImageCursor.setPosition(posInputImage);
					sum_gauche += (int) inputImageCursor.get().getRealDouble();
				}
				
				int sum_droite = 0;
				for(int i_droite = i; i_droite < i+longueur_changement; i_droite++) {
					posInputImage[0] = i_droite;
					inputImageCursor.setPosition(posInputImage);
					sum_droite += (int) inputImageCursor.get().getRealDouble();
				}
				*/
				
				// Si il detecte une bordure
				if(Math.abs(sum_gauche - sum_droite) > 128 && decompte_parcours_bordure < 0) {
					decompte_parcours_bordure = longueur_changement;
					changement = true;
				}
			}
			if(((double) inputImageCursor.get().getRealDouble()) > 128) { // TODO Dafuq le fuck ?
				// Si c'est blanc
				if(debut) {
					// pass
					System.out.println("debut blanc");
				} else if(nb_bit <= 2) {
					if(dernier_noir) {
						tab_bits[nb_bit] = 0;
						nb_bit++;
						nb_pixels_last_change++;
						longueur_bit = (longueur_bit + nb_pixels_last_change)/2;
						nb_pixels_last_change = 0;
						System.out.println("vers debut est devenu blanc");
					} else {
						System.out.println("vers debut reste blanc");
						nb_pixels_last_change++;
						// pass
					}
				} else if(!dernier_noir && nb_pixels_last_change > longueur_bit && ((nb_pixels_last_change + longueur_bit / 3) % longueur_bit) == 0) {
					System.out.println("nb_pixels_last_change : " + nb_pixels_last_change);
					System.out.println("longueur_bit : " + longueur_bit);
					nb_pixels_last_change++;
					tab_bits[nb_bit] = 0;
					nb_bit++;
					System.out.println("reste blanc, nouveau bit");
				} else if(dernier_noir) {
					nb_pixels_last_change = 0;
					tab_bits[nb_bit] = 0;
					nb_bit++;
					System.out.println("est devenu blanc");
				} else if(!dernier_noir) {
					nb_pixels_last_change++;
					System.out.println("reste blanc");
				}
				dernier_noir = false;
				
			} else {
				// Si c'est noir
				if(debut) {
					debut = false;
					tab_bits[nb_bit] = 1;
					nb_bit++;
					nb_pixels_last_change++;
					System.out.println("tout début, noir");
				} else if (nb_bit <= 2) {
					if(dernier_noir) {
						// pass
						System.out.println("vers debut reste noir");
						nb_pixels_last_change++;
					} else {
						System.out.println("vers debut est devenu noir");
						tab_bits[nb_bit] = 1;
						nb_bit++;
						nb_pixels_last_change++;
						longueur_bit = (longueur_bit + nb_pixels_last_change)/2;
						nb_pixels_last_change = 0;
					}
				} else if(dernier_noir && nb_pixels_last_change > longueur_bit && ((nb_pixels_last_change + longueur_bit / 3) % longueur_bit) == 0) {
					System.out.println("nb_pixels_last_change : " + nb_pixels_last_change);
					System.out.println("longueur_bit : " + longueur_bit);
					nb_pixels_last_change++;
					tab_bits[nb_bit] = 1;
					nb_bit++;
					System.out.println("reste noir, nouveau bit");
				} else if(!dernier_noir) {
					nb_pixels_last_change = 0;
					tab_bits[nb_bit] = 1;
					nb_bit++;
					System.out.println("est devenu noir");
				} else if(dernier_noir) {
					nb_pixels_last_change++;
					System.out.println("reste noir");
				}
				dernier_noir = true;
			}
		}
		
		String resultat = "";
		System.out.println("");
		for(int i = 0; i<tab_bits.length; i++) {
			resultat += "" + tab_bits[i];
			System.out.print("" + tab_bits[i]);
		}
		System.out.println("");
		
		System.out.println("Resultat final : " + resultat);
		
		return resultat;
		
	}
}
