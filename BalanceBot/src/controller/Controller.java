package controller;

import persistence.Genome;

public abstract class Controller {
	
	private Genome genome;
	
	public void setGenome(Genome genome) {
		this.genome = genome;
	}
	
	public Genome getGenome() {
		return genome;
	}
	
	public abstract long run();
}
