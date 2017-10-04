package simulation;

public abstract class DNA<T> implements Cloneable {
	
	private T[] genes;
	
	public abstract void update();
	
	public void mutate() {
		
		//TODO
		
	}
	
	@Override
	public DNA<T> clone() {
		DNA<T> copy = new DNA<T>(genes.length);
		copy.genes = genes;
		return copy;
	}
	
	public DNA(int nGenes) {
		
		genes = new T[nGenes];
			
	}
	
}
