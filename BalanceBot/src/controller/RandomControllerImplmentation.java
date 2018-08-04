package controller;

import java.util.Random;

public class RandomControllerImplmentation extends Controller {
	
	private static Random rng = new Random();

	@Override
	public long run() {
		float[] genes = getGenome().getGenes();
		
		float a = genes[0] * 3;
		float b = genes[1] * -2;
		float c = genes[2] * -1;
		float d = genes[3] * 7;
		
		return Math.round(rng.nextGaussian() * a + rng.nextGaussian() * b + rng.nextGaussian() * c + rng.nextGaussian() * d);
	}
	
}
