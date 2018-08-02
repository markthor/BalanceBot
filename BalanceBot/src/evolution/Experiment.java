package evolution;

import persistence.Genome;

public class Experiment {
	private long fitness;
	private Genome genome;
	
	public long getFitness() {
		return fitness;
	}
	public void setFitness(long fitness) {
		this.fitness = fitness;
	}
	public Genome getGenome() {
		return genome;
	}
	public void setGenome(Genome genome) {
		this.genome = genome;
	}
	
	
}
