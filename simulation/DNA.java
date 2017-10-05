package simulation;

public class DNA implements Cloneable {
	
	private double[] genes;
	private double min;
	private double max;
	private double step = 1;
	
	public abstract void update();
	
	public void mutate() {
		
		int index = (int) Math.round(Math.random()*(genes.length-1));
		mutateGene(index);
		
	}
	
	private void mutateGene(int index) {
		
		genes[index] = (int) Math.round(Math.random()*(max-min)/step)*step+min;
		
	}
	
	public DNA randomClone() {
		DNA copy = clone();
		for (int i = 0; i < genes.length) {
			mutateGene(i);
		}
		return copy;
	}
	
	@Override
	public DNA clone() {
		DNA copy = new DNA(genes.length);
		copy.genes = genes;
		copy.min = min;
		copy.max = max;
		copy.step = step;
		return copy;
	}
	
	public DNA(int nGenes, double min, double max) {
		
		this.min = min;
		this.max = max;
		genes = new double[nGenes];
			
	}
	
}
