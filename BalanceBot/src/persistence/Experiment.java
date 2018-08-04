package persistence;

import java.util.Date;

public class Experiment implements Storable {
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

	@Override
	public String getName() {
		return getClass().getCanonicalName();
	}
}
