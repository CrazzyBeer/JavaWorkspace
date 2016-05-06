package KnapsackProblem.Algorithm;

import java.util.Vector;

import org.uncommons.maths.random.MersenneTwisterRNG;

import edu.princeton.cs.algs4.MaxPQ;

public class EvolutionaryAlgorithm {
    private MaxPQ<Organism> organismsSorted;
    private Vector<Organism> currentGeneration;
    private int number;
    private int generation;
    private int fitnessSum; // Sum of all the fitness qualifiers in the array currentGeneration
    private MersenneTwisterRNG rand; // en.wikipedia.org/wiki/Mersenne_Twister
    private double crossoverProbability = 0.85;

    public EvolutionaryAlgorithm(int startingNumber) {
	if (startingNumber < 2)
	    throw new IllegalArgumentException("The starting number can't be less than 2");
	this.number = startingNumber;

	rand = new MersenneTwisterRNG();
	rand.setSeed(System.nanoTime());

	fitnessSum = 0;
	generation = 0;
	organismsSorted = new MaxPQ<>();
	currentGeneration = new Vector<>();

	for (int i = 0; i < startingNumber; i++) {
	    Organism t = new Organism();
	    organismsSorted.insert(t);
	    currentGeneration.add(t);
	    fitnessSum += t.getFitness();
	    // System.out.printf("Org: [%s], Fitness: %d \n", t.getChromosome().getCode(), t.getFitness());
	    // System.out.println(t.getCode() + " " + t.getFitness() + " " + t.getResultWeight());
	}
	// System.out.println("Created generation 0. Size : " + currentGeneration.size());
    }

    /**
     * Executes a specific number of generations
     * Notice that the used method is generationSampling
     * @see EvolutionaryAlgorithm#nextGenerationSampling()
     * @param generations - the number of generations to iterate
     */
    public void process(int generations) {
	while (generations > 0) {
	    generations--;
	    nextGenerationSampling();
	}
    }

    /**
     * Returns
     * @return organismsSorted.max()
     */
    public Organism getCurrentBest() {
	return organismsSorted.max();
    }

    /**
     * @return The current generation in the population
     */
    public int getGeneration() {
	return generation;
    }

    /**
     * @return The size of the population
     */
    public int getSize() {
	return organismsSorted.size();
    }

    /**
     * Tournament selection
     * https://en.wikipedia.org/wiki/Tournament_selection
     */
    public void nextGenerationTournament() {
	generation++;
	MaxPQ<Organism> newOrganismsSorted = new MaxPQ<>();
	Vector<Organism> newGeneration = new Vector<>();
	Organism lastParent = null;
	int newFitnessSum = 0;
	int elite = 2; // 2 elite individuals
	
	// Adding the elite population
	for (int i = 0; i < elite; i++) {
	    Organism pop = organismsSorted.delMax();
	    newGeneration.add(pop);
	    newOrganismsSorted.insert(pop);
	    newFitnessSum += pop.getFitness();
	}

	while (newGeneration.size() < number) {
	    int first = rand.nextInt(number);
	    int second = rand.nextInt(number);

	    Organism one = currentGeneration.elementAt(first);
	    Organism two = currentGeneration.elementAt(second);
	    Organism next = one;

	    if (one.getFitness() < two.getFitness())
		next = two;
	    if (lastParent == null) {
		newGeneration.add(next);
		newOrganismsSorted.insert(next);
		lastParent = next;
	    } else {
		Organism offspring1 = new Organism(next.getCode());
		Organism offspring2 = new Organism(lastParent.getCode());
		// System.out.printf(" %s + %s %d %d \n", next.getChromosome().getCode(), lastParent.getChromosome().getCode(),
		// next.getFitness(), lastParent.getFitness());
		double crossover = rand.nextDouble();
		if (crossover <= crossoverProbability) {
		    int crossoverPosition = rand.nextInt(next.getChromosome().getLength() - 2) + 1;
		    offspring1 = new Organism(next.getChromosome().getPart(crossoverPosition, 1),
			    lastParent.getChromosome().getPart(crossoverPosition, 2));
		    offspring2 = new Organism(lastParent.getChromosome().getPart(crossoverPosition, 1),
			    next.getChromosome().getPart(crossoverPosition, 2));
		}

		newGeneration.add(offspring1);
		newOrganismsSorted.insert(offspring1);
		newFitnessSum += offspring1.getFitness();

		if (newGeneration.size() < number) {
		    newGeneration.add(offspring2);
		    newOrganismsSorted.insert(offspring2);
		    newFitnessSum += offspring2.getFitness();
		}

		lastParent = next;
	    }
	}
	currentGeneration = newGeneration;
	organismsSorted = newOrganismsSorted;
	fitnessSum = newFitnessSum;

    }

    /**
     * The selection method uses Stochastic Universal Sampling
     * Stochastic Universal Sampling is an elaborately-named variation of roulette wheel selection. Stochastic Universal Sampling ensures that the observed selection frequencies of each individual are in line with the expected frequencies. So if we
     * have an individual that occupies 4.5% of the wheel and we select 100 individuals, we would expect on average for that individual to be selected between four and five times. Stochastic Universal Sampling guarantees this. The individual will be
     * selected either four times or five times, not three times, not zero times and not 100 times. Standard roulette wheel selection does not make this guarantee.
     * 
     * Also, there is a 5% transfer of the elite population.
     * The algorithm is similar to the one presented in
     * https://en.wikipedia.org/wiki/Stochastic_universal_sampling
     * http://www.micsymposium.org/mics_2004/Hristake.pdf
     */
    public void nextGenerationSampling() {
	generation++;
	MaxPQ<Organism> newOrganismsSorted = new MaxPQ<>();
	Vector<Organism> newGeneration = new Vector<>();
	int newFitnessSum = 0;

	int fivePercent = (int) Math.ceil((double) number / 20); // 5% elite. If the number is less than 20, the number get rounded
	// System.out.printf("%d Have been selected as elite \n", fivePercent);

	// Adding the elite population
	for (int i = 0; i < fivePercent; i++) {
	    Organism pop = organismsSorted.delMax();
	    newGeneration.add(pop);
	    newOrganismsSorted.insert(pop);
	    newFitnessSum += pop.getFitness();

	}
	int pairs = (number - fivePercent) / 2;
	// System.out.printf("Elite : %d, %d Remaining spots Pairs %d \n", newGeneration.size(), number - fivePercent,
	// pairs);

	for (int i = 0; i < pairs; i++) {
	    // System.out.println(i);
	    boolean accepted1 = false;
	    boolean accepted2 = false;
	    int position1 = 0;
	    int position2 = 0;
	    while (!accepted1) {
		position1 = rand.nextInt(number);

		double probability = (double) currentGeneration.elementAt(position1).getFitness() / fitnessSum;
		double randDouble = rand.nextDouble();
		// System.out.printf("Position: %d, probability %f, rand %f \n", position, probability, randDouble);
		if (randDouble <= probability) {
		    accepted1 = true;
		}
	    }
	    while (!accepted2) {
		position2 = rand.nextInt(number);

		double probability = (double) currentGeneration.elementAt(position2).getFitness() / fitnessSum;
		double randDouble = rand.nextDouble();
		// System.out.printf("Position: %d, probability %f, rand %f \n", position, probability, randDouble);
		if (randDouble <= probability) {
		    accepted2 = true;
		}
	    }
	    Organism parent1 = currentGeneration.elementAt(position1);
	    Organism parent2 = currentGeneration.elementAt(position2);
	    Organism offspring1 = new Organism(parent1.getCode());
	    Organism offspring2 = new Organism(parent2.getCode());
	    // System.out.printf(" %s + %s %d %d \n", parent1.getChromosome().getCode(), parent2.getChromosome().getCode(),
	    // parent1.getFitness(), parent2.getFitness());
	    double crossover = rand.nextDouble();
	    if (crossover <= crossoverProbability) {
		int crossoverPosition = rand.nextInt(parent1.getChromosome().getLength() - 2) + 1;
		offspring1 = new Organism(parent1.getChromosome().getPart(crossoverPosition, 1),
			parent2.getChromosome().getPart(crossoverPosition, 2));
		offspring2 = new Organism(parent2.getChromosome().getPart(crossoverPosition, 1),
			parent1.getChromosome().getPart(crossoverPosition, 2));
		/*
		 * System.out.printf("  Crossover! 1.1 [%s] , 2.2 [%s]     2.1 [%s] 1.2 [%s] \n",
		 * parent1.getChromosome().getPart(crossoverPosition, 1),
		 * parent2.getChromosome().getPart(crossoverPosition, 2),
		 * parent2.getChromosome().getPart(crossoverPosition, 1),
		 * parent1.getChromosome().getPart(crossoverPosition, 2));
		 */
	    }
	    newFitnessSum += offspring1.getFitness();
	    newFitnessSum += offspring2.getFitness();

	    newGeneration.add(offspring1);
	    newGeneration.add(offspring2);
	    newOrganismsSorted.insert(offspring1);
	    newOrganismsSorted.insert(offspring2);

	}
	if (newGeneration.size() < number) {
	    Organism random = new Organism();
	    newGeneration.addElement(random);
	    newOrganismsSorted.insert(random);
	}
	fitnessSum = newFitnessSum;
	currentGeneration = newGeneration;
	organismsSorted = newOrganismsSorted;
	// System.out.println(organismsSorted.size() + " " + currentGeneration.size());
    }

    /**
     * @return avg - Rounded value of the average fitness
     */
    public int getAverage() {
	int avg = fitnessSum / number;
	return avg;
    }

    /**
     * 
     * @return the whole sorted population
     */
    public MaxPQ<Organism> getPQ() {
	return organismsSorted;
    }


}
