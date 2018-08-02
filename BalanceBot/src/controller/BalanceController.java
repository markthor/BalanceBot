package controller;

import persistence.Genome;

public interface BalanceController {
	public void setGenome(Genome genome);
	public long run();
}
