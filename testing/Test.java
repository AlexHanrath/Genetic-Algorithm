package testing;

import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import simulation.*;

public class Test {
	
	public static class TextDNA extends DNA {
		
		public static Function<List<DNA>, List<Double>> getFitness = dnas -> dnas.parallelStream().map(dna -> ((TextDNA) dna).countMatch(target)).collect(Collectors.toList());
		
		public double countMatch(String target) {
			double count = 0;
			for (int i = 0; i < genes.length; i++) {
				if (charMap[(int) genes[i]] == target.charAt(i)) {
					count++;
				}
			}
			return count;
		}
		
		public TextDNA(int nGenes, double min, double max) {
			super(nGenes, min, max, 1);
		}
		
		@Override
		public void update() {}
		@Override
		public DNA clone() {
			TextDNA copy = new TextDNA(genes.length, min, max);
			copy.genes = new double[genes.length];
			System.arraycopy(genes, 0, copy.genes, 0, genes.length);
			return copy;
		}
		
		@Override
		public String toString() {
			String res = "";
			for (int i = 0; i < genes.length; i++) {
				res += charMap[(int) genes[i]];
			}
			return res;
		}
		
	}
	
	public static final char[] charMap = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '};
	public static final String target = "hello world";
	public static final int size = 100;
	public static final int nGenerations = 1000;
	public static final int outputInterval = 10;
	
	public static void main(String[] args) {
    		
		TextDNA template = new TextDNA(target.length(), 0, charMap.length-1);
    		Population p = new Population(TextDNA.getFitness, template, size);
		
		for (int i = 0; i < nGenerations; i += outputInterval) {
			p.doGenerations(nGenerations);
			List<DNA> pool = p.getPool();
			List<Double> fitnesses = TextDNA.getFitness.apply(pool);
			int index = 0;
			for (int j = 0; j < fitnesses.size(); j++) {
				index = (fitnesses.get(j) > fitnesses.get(index)) ? j : index;
			}
			System.out.println(pool.get(index).toString());
		}
    		
	}
	
}
