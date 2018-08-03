package evolution;

import java.util.Date;

import persistence.Genome;

public class Experiment {
	private long fitness;
	private Genome genome;
	private Date timeStamp;
	
	public Experiment(long fitness, Genome genome) {
		super();
		this.fitness = fitness;
		this.genome = genome;
		timeStamp = new Date();
	}

	public long getFitness() {
		return fitness;
	}

	public Genome getGenome() {
		return genome;
	}
	
	@Override
	public String toString() {
		return "Experiment at " + timeStamp + " for genome " + " resulted in fitness of " + fitness;
	}
}
