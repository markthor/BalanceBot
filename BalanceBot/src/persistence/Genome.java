package persistence;

import java.util.Arrays;
import java.util.Random;

import evolution.Evolver;

public class Genome implements Storable {
	private static Random rng = new Random();
	private static int numberOfGenes = 4;
	private float[] genes;
	private int hashCode;
	
	public Genome() {
		initialize();
	}
	
	private void initialize() {
		genes = new float[numberOfGenes];
		mutate();
		
		for(float f : genes) {
			hashCode += Float.hashCode(f);
		}
	}
	
	public void mutate() {
		float mutationFactor = (float) ((float) rng.nextGaussian() * Evolver.MUTATION_INTENSITY);
		int mutateIndex = rng.nextInt(genes.length);
		genes[mutateIndex] = genes[mutateIndex] + mutationFactor;
	}
	
	public float[] getGenes() {
		return genes;
	}
	
	@Override
	public String toString() {
		return "HashCode = " + hashCode + " Genes = " + Arrays.toString(genes);
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public String getName() {
		return getClass().getTypeName();
	}
}
