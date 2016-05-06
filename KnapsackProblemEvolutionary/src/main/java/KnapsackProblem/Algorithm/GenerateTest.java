package KnapsackProblem.Algorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateTest {

    public static void main(String[] args) {
	if (args.length == 2) {
	    int n = Integer.parseInt(args[0]);
	    int maxWeight = Integer.parseInt(args[1]);
	    Random rand = new Random(System.nanoTime());
	    int inputName = 1;
	    File file = new File("input" + inputName + ".txt");
	    while (file.exists()) {
		inputName++;
		file = new File("input" + inputName + ".txt");
	    }
	    try {
		PrintWriter pw = new PrintWriter(new FileWriter(file));
		pw.printf("%d %d\n", maxWeight, n);
		for (int i = 0; i < n; i++) {
		    int weight = rand.nextInt(maxWeight);
		    int cost = rand.nextInt(5000);
		    pw.printf("%d %d\n", weight, cost);
		}
		pw.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

	}

    }

}
