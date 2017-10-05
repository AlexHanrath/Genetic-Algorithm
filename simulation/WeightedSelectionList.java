package simulation;

import java.util.Map;
import java.util.HashMap;

public class WeightedSelectionList<T> {
	
	private Map<T, Double> data = new HashMap<T, Double>();
	private double total = 0;
	
	public void add(double weight, T elem) {
		
		remove(elem);
		
		data.put(elem, weight);
		total += weight;
		
	}
	
	public void remove(T elem) {
		
		if (data.containsKey(elem)) {
			
			total -= data.get(elem);
			data.remove(elem);
			
		}
		
	}
	
	public T removeRandom() {
		
		double v = Math.random()*total;
		T sel = null;
		
		for (Entry<T, Double> entry : data.entrySet()) {
			
			v -= entry.getValue();
			
			if (v <= 0) {
				
				sel = entry.getKey();
				break;
				
			}
			
		}
		
		remove(sel);
		return sel;
		
	}
	
}
