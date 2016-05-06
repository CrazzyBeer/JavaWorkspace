package KnapsackProblem.Algorithm;

public class Chromosome {
    private int chromosomeCost;
    private int chromosomeWeight;
    private StringBuilder chromosomeString;
    private int length;

    public Chromosome(int chromosomeCost, int chromosomeWeight, StringBuilder chromosomeString) {
	this.chromosomeCost = chromosomeCost;
	this.chromosomeWeight = chromosomeWeight;
	this.chromosomeString = chromosomeString;
	this.length = chromosomeString.length();
    }

    public Chromosome() {
	this.chromosomeCost = 0;
	this.chromosomeWeight = 0;
	this.chromosomeString = new StringBuilder();
    }

    public void mutate(int pos, int weight, int cost) {
	if (chromosomeString.charAt(pos) == '0') {
	    chromosomeString.setCharAt(pos, '1');
	    chromosomeCost += cost;
	    chromosomeWeight += weight;

	} else {
	    chromosomeString.setCharAt(pos, '0');
	    chromosomeCost -= cost;
	    chromosomeWeight -= weight;
	}
    }

    public void simplify(int pos, int weight, int cost) {
	if (chromosomeString.charAt(pos) == '1') {
	    chromosomeString.setCharAt(pos, '0');
	    chromosomeCost -= cost;
	    chromosomeWeight -= weight;
	}
    }
    /**
     * 
     * @param pos position of the cut [0, len-2]
     * a|bcd -> pos = 0
     * ab|cd -> pos = 1
     * abc|d -> pos = 2
     * @param part the part of the string given {1, 2}, the first and the second part of the cut
     * @return
     */
    public String getPart(int pos, int part) {
	if (part == 1)
	    return chromosomeString.substring(0, pos + 1);
	else
	    return chromosomeString.substring(pos + 1);
    }

    public int getChromosomeCost() {
        return chromosomeCost;
    }

    public void setChromosomeCost(int chromosomeCost) {
        this.chromosomeCost = chromosomeCost;
    }

    public int getChromosomeWeight() {
        return chromosomeWeight;
    }

    public void setChromosomeWeight(int chromosomeWeight) {
        this.chromosomeWeight = chromosomeWeight;
    }

    public StringBuilder getChromosomeString() {
        return chromosomeString;
    }

    public void setChromosomeString(StringBuilder chromosomeString) {
        this.chromosomeString = chromosomeString;
    }
    
    public char getCharAt(int pos) {
	return chromosomeString.charAt(pos);
    }
    
    public String getCode() {
	return chromosomeString.toString();
    }
   
    public int getLength() {
	return length;
    }
    

}
