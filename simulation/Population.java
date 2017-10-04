package simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Population {
	
	private DNA type;
	private List<DNA> pool = new ArrayList<DNA>();
	private Consumer<List<DNA>> onUpdate;
	
	private double mutationChange = 0.01;
	private double selectionPressure = 0.01;
	private double generationRefreshFactor = 2;
	
	public Population(DNA type, int poolSize) {
		
		this.type = type;
		
		initPool(poolSize);
		
	}
	
	public Population(DNA type, int poolSize, Consumer<List<DNA<T>>> onUpdate) {
		
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
		
		//TODO create weighted list
		
		for (int i = 0; i < generationRefreshFactor; i++) {
			//TODO remove random child from said list
		}
		
	}
	
	private void initPool(int poolSize) {
		
		for (int i = 0; i < size; i++) {
			
			pool.add(type.randomClone());
			
		}
		
	}
	
	private void createChildren() {
		
		List<DNA> children = new ArrayList<DNA>();
		for (DNA parent : pool) {
			for (int i = 0; i < generationRefreshFactor; i++) {
				DNA child = parent.clone();
				if (Math.random() < mutationRate) {
					child.mutate();
				}
				children.add(child);
			}
		}
		
		pool.clear();
		pool.addAll(children);
		
	}
	
	private void reproduce() {
		
		select();
		createChildren();
		
	}
	
}
