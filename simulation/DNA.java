package simulation;

public abstract class DNA {
	
	protected double[] genes;
	protected double min;
	protected double max;
	protected double step = 1;
	
	public abstract void update();
	public abstract DNA clone();
	
	public void mutate() {
		
		int index = (int) Math.round(Math.random()*(genes.length-1));
		mutateGene(index);
		
	}
	
	private void mutateGene(int index) {
		
		genes[index] = (int) Math.round(Math.random()*(max-min)/step)*step+min;
		
	}
	
	public DNA randomClone() {
		DNA copy = clone();
		for (int i = 0; i < genes.length; i++) {
			mutateGene(i);
		}
		return copy;
	}
	
	public DNA(int nGenes, double min, double max, double step) {
		
		this.step = step;
		this.min = min;
		this.max = max;
		genes = new double[nGenes];
			
	}
	
}
