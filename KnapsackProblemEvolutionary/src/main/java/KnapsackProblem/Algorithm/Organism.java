package KnapsackProblem.Algorithm;

import java.util.Vector;

import org.uncommons.maths.random.MersenneTwisterRNG;

public class Organism implements Comparable<Organism> {
    private int resultWeight;
    private int resultCost;

    private Chromosome chromosome;

    private int fitness;

    private static Vector<Pair<Integer, Integer>> items;
    private static int maxWeight;
    private static int length;
    private static MersenneTwisterRNG rand;

    private double mutationProbability = 0.05;

    /**
     * Create random organism, calculate partial sums (firstHalf and Second half)
     * 1. Generate two random genes, perform calculations on them
     * 2. Calculate the total fitness of the organism using both genes
     * 
     * @param length
     *            : the length of the code (N items)
     * @param items
     *            : the array of items
     * @param maxWeight
     *            : the maximum possible weight
     */
    public Organism() {

	chromosome = createChromosome();

	simplify();
	updateResults();

    }
    /** TODO DRY! **/
    public Organism(String code) {
	if (code.length() == 0)
	    throw new IllegalArgumentException("The given string can't be of length 0");
	
	StringBuilder fullCode = new StringBuilder(code);
	int weight = 0;
	int cost = 0;
	for (int i = 0; i < length; i++) {
	    double mutation = rand.nextDouble();
	    if (mutation <= mutationProbability) {
		if (fullCode.charAt(i) == '0')
		    fullCode.setCharAt(i, '1');
		else
		    fullCode.setCharAt(i, '0');
	    }

	    if (fullCode.charAt(i) == '1') {
		weight += items.elementAt(i).x;
		cost += items.elementAt(i).y;
	    }
	}
	chromosome = new Chromosome(cost, weight, fullCode);
	
	simplify();
	updateResults();
    }

    public Organism(String first, String second) {
	if (first.length() == 0 || second.length() == 0)
	    throw new IllegalArgumentException("The given strings can't be of length 0");

	StringBuilder fullCode = new StringBuilder(first + second);
	int weight = 0;
	int cost = 0;

	// Weight & cost summation as well as point mutation with a given probability
	for (int i = 0; i < length; i++) {
	    double mutation = rand.nextDouble();
	    if (mutation <= mutationProbability) {
		if (fullCode.charAt(i) == '0')
		    fullCode.setCharAt(i, '1');
		else
		    fullCode.setCharAt(i, '0');
	    }

	    if (fullCode.charAt(i) == '1') {
		weight += items.elementAt(i).x;
		cost += items.elementAt(i).y;
	    }
	}

	chromosome = new Chromosome(cost, weight, fullCode);

	simplify();
	updateResults();
    }

    public static void prepare(Vector<Pair<Integer, Integer>> items, int maxWeight) {
	if (items.size() == 0)
	    throw new IllegalArgumentException("Length can't be zero");
	Organism.items = items;
	Organism.maxWeight = maxWeight;
	Organism.length = items.size();
	rand = new MersenneTwisterRNG();
	rand.setSeed(System.nanoTime());
    }

    /**
     * Generates a chromosome with random genetic code
     * 
     * @return
     */
    public Chromosome createChromosome() {
	StringBuilder chromosomeCode = new StringBuilder();
	int currentPos = 0;
	int chromosomeCost = 0;
	int chromosomeWeight = 0;
	while (currentPos < length) {
	    int randomInt = rand.nextInt(2);
	    if (randomInt == 0) {
		chromosomeCode.append('0');
	    } else {
		chromosomeCode.append('1');
		chromosomeCost += items.elementAt(currentPos).y;
		chromosomeWeight += items.elementAt(currentPos).x;
	    }
	    currentPos++;

	}
	return new Chromosome(chromosomeCost, chromosomeWeight, chromosomeCode);
    }

    public void simplify() {
	while (chromosome.getChromosomeWeight() > maxWeight) {
	    int pos = rand.nextInt(length);
	    if (chromosome.getCharAt(pos) == '1')
		chromosome.simplify(pos, items.elementAt(pos).x, items.elementAt(pos).y);
	}
    }

    private void updateResults() {
	resultCost = chromosome.getChromosomeCost();
	resultWeight = chromosome.getChromosomeWeight();
	fitness = resultCost;
    }

    public int getFitness() {
	return fitness;
    }

    public Chromosome getChromosome() {
	return chromosome;
    }

    public int getResultWeight() {
	return resultWeight;
    }

    public int getResultCost() {
	return resultCost;
    }

    public String getCode() {
	return chromosome.getChromosomeString().toString();
    }

    @Override
    public int compareTo(Organism that) {
	if (this.fitness > that.fitness)
	    return 1;
	else if (this.fitness < that.fitness)
	    return -1;
	else if (this.resultWeight < that.resultWeight)
	    return 1;
	else if (this.resultWeight > that.resultWeight)
	    return -1;
	return 0;
    }

}
