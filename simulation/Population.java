package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Population {
	
	private DNA type;
	private List<DNA> pool = new ArrayList<DNA>();
	
	private Consumer<List<DNA>> onUpdate;
	private Function<List<DNA>, List<Double> getFitness;
	
	private double mutationChange = 0.01;
	private double selectionPressure = 0.01;
	private double generationRefreshFactor = 2;
	
	public Population(Function<List<DNA>, List<Double> getFitness, DNA type, int poolSize) {
		
		this.type = type;
		this.getFitness = getFitness;
		
		initPool(poolSize);
		
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
	
	public void doGenerations(int n) {
		
		for (int i = 0; i < n; i++) {
			update();
		}
		
	}
	
	public void doGenerationsAsync(int n, Consumer<Population> onComplete) {
		
		new Thread() {
			@Override
			public void run() {
				doGenerations(n);
				onComplete.accept(Population.this);
			}
		}.start();
		
	}
	
	public Future<Population> doGenerationsAsync(int n) {
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		return es.submit(new Callable<Population>() {
			@Override
			public Population call() {
				doGenerations(n);
				return Population.this;
			}
		});
		
	}
	
	private void select() {
		
		List<Double> fitnesses = getFitness.apply(new ArrayList<DNA>(pool));
			
		//TODO create weighted pool where selectionPressure is fuzzyness
		
		for (int i = 0; i < generationRefreshFactor; i++) {
			//TODO remove random child from the list
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
