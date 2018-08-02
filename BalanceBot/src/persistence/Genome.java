package persistence;

import java.util.Arrays;
import java.util.Random;

import evolution.Evolver;

public class Genome {
	private static Random rng = new Random();
	private static int numberOfGenes = 4;
	private int id;
	private float[] genes;
	
	public Genome() {
		initialize();
	}
	
	private void initialize() {
		genes = new float[numberOfGenes];
		mutate();
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
		return "Id = " + id + " Genes = " + Arrays.toString(genes);
	}
}
