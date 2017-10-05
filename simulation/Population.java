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
	
	public static double linearInterpolate(double a, double b, double v) {
		return a*(1-v)+b*v;
	}
	
	public static double quadraticInterpolate(double a, double b, double v) {
		return a*(1-v*v)+b*v*v;
	}
	
	public static double squareRootInterpolate(double a, double b, double v) {
		return a*(1-Math.sqrt(v))+b*Math.sqrt(v);
	}
	
	private DNA type;
	private List<Entry<DNA, Double>> pool = new ArrayList<Entry<DNA, Double>>();
	
	public List<DNA> getPool() {
		return new ArrayList<DNA>(pool);
	}
	
	private Function<List<DNA>, List<Double>> getFitness;
	
	private double mutationRate = 0.01;
	private double selectionPressure = 1;
	private double generationRefreshFactor = 2;
	
	public Population(Function<List<DNA>, List<Double>> getFitness, DNA type, int poolSize) {
		
		this.type = type;
		this.getFitness = getFitness;
		
		initPool(poolSize);
		
	}
	
	public void setMutationRate(double newValue) {
		mutationRate = newValue;	
	}
	
	public double getMutationRate() {
		return mutationRate;
	}
	
	public void setSelectionPressure(double newValue) {
		selectionPressure = newValue;	
	}
	
	public double getSelectionPressure() {
		return selectionPressure;	
	}
	
	private void update() {
		
		for (DNA o : pool) {
			
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
	
	public DNA getBest() {
		
		
		
	}
	
	public DNA getWorst() {
		
		
		
	}
	
	public DNA getMean() {
		
		
		
	}
	
	private void select() {
		
		if (pool.size() == 0) {
			return;
		}
		
		List<Double> fitnesses = getFitness.apply(new ArrayList<DNA>(pool));
		List<Entry<DNA, Double>> newPool = new ArrayList<Entry<DNA, Double>>();
		
		double average = fitnesses.parallelStream().mapToDouble(v -> v).average().getAsDouble();
		
		WeightedSelectionList<Entry<DNA, Double>> l = new WeightedSelectionList<Entry<DNA, Double>>();
		for (int i = 0; i < pool.size(); i++) {
			l.add(new SimpleEntry<DNA, Double>(pool.get(i), linearInterpolate(average, fitnesses.get(i), selectionPressure)), pool.get(i));
		}
		
		for (int i = 0; i < pool.size()*(generationRefreshFactor-1)/generationRefreshFactor; i++) {
			newPool.add(l.removeRandom());
		}
		
		pool.retainAll(newPool);
		
	}
	
	private void initPool(int poolSize) {
		
		for (int i = 0; i < poolSize; i++) {
			
			pool.add(new SimpleEntry<DNA, Double>(type.randomClone(), 0));
			
		}
		
	}
	
	private void createChildren() {
		
		List<Entry<DNA, Double>> children = new ArrayList<Entry<DNA, Double>>();
		for (Entry<DNA, Double> parent : pool) {
			for (int i = 0; i < generationRefreshFactor; i++) {
				DNA child = parent.getKey().clone();
				if (Math.random() < mutationRate) {
					child.mutate();
				}
				children.add(new SimpleEntry<DNA, Double>(child, parent.getValue()));
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
