package simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Population<T> {
	
	private DNA<T> type;
	private List<DNA<T>> pool = new ArrayList<Organism>();
	private Consumer<List<Organism>> onUpdate;
	
	private double mutationChange = 0.01;
	private double selectionPressure = 0.01;
	
	public Population(DNA<T> type, int poolSize) {
		
		this.type = type;
		
		initPool(poolSize);
		
	}
	
	public Population(DNA<T> type, int poolSize, Consumer<List<DNA<T>>> onUpdate) {
		
		this(type, poolSize);
		
		this.onUpdate = onUpdate;
		
	}
	
	public void setMutationChance(double newValue) {
		mutationChange = newValue;	
	}
	
	public double getMutationChance(double newValue) {
		return mutationChange;
	}
	
	public void setSelectionPressure(double newValue) {
		selectionPressure = newValue;	
	}
	
	public double getSelectionPressure(double newValue) {
		return selectionPressure;	
	}
	
	private void update() {
		
		for (Organism o : pool) {
			
			o.update();
			
		}
		
		reproduce();
		
	}
	
	private void select() {
		
		//TODO
		
	}
	
	private void initPool(int poolSize) {
		
		for (int i = 0; i < size; i++) {
			
			pool.add(type.randomClone());
			
		}
		
	}
	
	private void createChildren() {
		
		//TODO
		
	}
	
	private void reproduce() {
		
		select();
		createChildren();
		
	}
	
}
