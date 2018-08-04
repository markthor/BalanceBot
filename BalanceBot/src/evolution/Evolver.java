package evolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import controller.Controller;
import controller.RandomControllerImplmentation;
import persistence.Experiment;
import persistence.Genome;
import persistence.Persistence;
import util.Log;

public class Evolver {
	//Evolution parameters
	public static final float MUTATION_INTENSITY = 1.0F;
	public static final int POPULATION_SIZE = 10;
	
	public static void main(String[] args) {
		evolve(10);
	}
	
	public static void evolve(int generations) {
		Log.log("Evolving for " + generations + " with a population size of " + POPULATION_SIZE);
		
		List<Experiment> experiments = null;
		int startFrom = Persistence.getLatestGenerationNumber();
		
		for(int generation = startFrom; generation <= generations + startFrom; generation++) {
			Log.log("Selecting " + POPULATION_SIZE + " genomes for evaluation in generation " + generation);
			List<Genome> population = getNextGeneration(experiments);
			
			Log.log("Evaluating genomes");
			experiments = evaluateGenomes(population);
			
			Log.log("Persisting genomes and experiments");
			Persistence.saveObjects(population, generation);
			Persistence.saveObjects(experiments, generation);
		}
	}
	
	private static List<Genome> getNextGeneration(List<Experiment> experiments) {
		List<Genome> result = new ArrayList<Genome>();
		//if(experiments.isEmpty()) {
			for(int i = 0; i < POPULATION_SIZE; i++) {
				result.add(new Genome());
			}
		///}
		return result;
	}
	
	private static List<Experiment> evaluateGenomes(List<Genome> genomes) {
		List<Experiment> result = new ArrayList<Experiment>();
		for(Genome genome : genomes) {
			long fitness = getController(genome).run();
			result.add(new Experiment(fitness, genome));
		}
		return result;
	}
	
	private static List<Genome> getBestGenomes(List<Experiment> experiments, int numberOfGenomesToReturn) {
		Map<Genome, Double> averageFitnessPerGenome = getAverageFitnessPerGenome(experiments);
		
		for(Entry<Genome, Double> entry : averageFitnessPerGenome.entrySet()) {
			
		}
		
		throw new UnsupportedOperationException();
	}
	
	private static Map<Genome, Double> getAverageFitnessPerGenome(List<Experiment> experiments) {
		Map<Genome, List<Long>> genomeFitness = new HashMap<Genome, List<Long>>();
		for(Experiment e : experiments) {
			if(!genomeFitness.containsKey(e.getGenome())) {
				List<Long> fitness = new ArrayList<Long>();
				genomeFitness.put(e.getGenome(), fitness);
			}
			genomeFitness.get(e.getGenome()).add(e.getFitness());
		}
		
		Map<Genome, Double> result = new HashMap<Genome, Double>();
		
		for(Entry<Genome, List<Long>> entry : genomeFitness.entrySet()) {
			result.put(entry.getKey(), getAverage(entry.getValue()));
		}
		
		return result;
	}
	
	private static Double getAverage(List<Long> longs) {
		if(longs.isEmpty()) {
			throw new IllegalArgumentException("Cannot calculate an average of an empty list");
		}
		double result = 0.0;
		for(Long l : longs) {
			result += (double) l;
		}
		return result / longs.size();
	}
	
	private static Controller getController(Genome genome) {
		Controller balanceController = new RandomControllerImplmentation();
		balanceController.setGenome(genome);
		return balanceController;
	}
}
