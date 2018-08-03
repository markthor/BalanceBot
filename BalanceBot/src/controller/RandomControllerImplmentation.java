package controller;

import java.util.Random;

public class RandomControllerImplmentation extends Controller {
	
	private static Random rng = new Random();

	@Override
	public long run() {
		float[] genes = getGenome().getGenes();
		
		float a = genes[1] * 3;
		float b = genes[2] * -2;
		float c = genes[3] * -1;
		float d = genes[4] * 7;
		
		return Math.round(rng.nextGaussian() * a + rng.nextGaussian() * b + rng.nextGaussian() * c + rng.nextGaussian() * d);
	}
	
}
