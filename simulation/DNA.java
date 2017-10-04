package simulation;

public class DNA implements Cloneable {
	
	private int[] genes;
	private int min;
	private int max;
	
	public abstract void update();
	
	public void mutate() {
		
		int index = Math.round(Math.random()*(genes.length-1));
		mutateGene(index);
		
	}
	
	private void mutateGene(int index) {
		
		genes[index] = Math.round(Math.random()*(max-min))+min;
		
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
		return copy;
	}
	
	public DNA(int nGenes, int min, int max) {
		
		this.min = min;
		this.max = max;
		genes = new int[nGenes];
			
	}
	
}
