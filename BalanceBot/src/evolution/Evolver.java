package evolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import persistence.Genome;
import persistence.Persistence;
import util.Log;

public class Evolver {
	//Evolution parameters
	public static final float MUTATION_INTENSITY = 1.0F;
	public static final int POPULATION_SIZE = 10;
	public static final int ELITIST_SIZE = 4;
	
	public static void main(String[] args) {
		
	}
	
	public static void evolve(int generations) {
		Log.log("Evolving for " + generations + " with a population size of " + POPULATION_SIZE);
		Log.logDebug("Mutation intensity = " + MUTATION_INTENSITY);
		Log.logDebug("Elitist = " + ELITIST_SIZE);
		
		List<Experiment> experiments = null;
		
		for(int generation = 1; generation <= generations; generation++) {
			Log.log("Selecting " + POPULATION_SIZE + " genomes for evaluation in generation " + generation);
			List<Genome> population = getNextGeneration(experiments);
			
			Log.log("Evaluating genomes");
			experiments = evaluateGenomes(population);
			
			Log.log("Persisting genomes");
			Persistence.saveExperiments(experiments);
		}
	}
	
	private static List<Genome> getNextGeneration(List<Experiment> experiments) {
		throw new UnsupportedOperationException();
	}
	
	private static List<Experiment> evaluateGenomes(List<Genome> genomes) {
		throw new UnsupportedOperationException();
	}
	
	private static Map<Genome, Double> getAverageFitnessPerGenome(List<Experiment> experiments) {
		Map<Genome, List<Long>> genomeFitness = new HashMap<Genome, List<Long>>();
		for(Experiment e : experiments) {
			if(genomeFitness.containsKey(e.getGenome())) {
				genomeFitness.get(e.getGenome()).add(e.getFitness());
			} else {
				List<Long> fitness = new ArrayList<Long>();
				fitness.add(e.getFitness());
				genomeFitness.put(e.getGenome(), fitness);
			}
		
		}
		
		Map<Genome, Double> result = new HashMap<Genome, Double>();
		
		for(Entry<Genome, List<Long>> entry : genomeFitness.entrySet()) {
			// IMPLEMENT
		}
		
		return null;
	}
}
