package KnapsackProblem.Algorithm;

import java.util.Vector;

public class KnapsackDP {
    public static Pair<Vector<Integer>, Integer> solve(Vector<Pair<Integer, Integer>> items, int maxWeight) {
	int n = items.size();
	int dp[][] = new int[n+2][maxWeight+2];

	for (int i = 1; i<=n; i++) {
	    int weight = items.elementAt(i-1).x;
	    int cost = items.elementAt(i-1).y;
	    for (int j = 0; j<=maxWeight; j++) {
		    dp[i][j] = dp[i-1][j];
		    if (j >= weight) dp[i][j] = Math.max(dp[i][j], dp[i-1][j - weight] + cost);
	    }
	}
	Vector <Integer> resultingItems = new Vector <> ();
	int currentWeight = maxWeight;
	int currentItem = n;
	while (true) {
	    if (dp[currentItem][currentWeight] != dp[currentItem-1][currentWeight]) {
		currentWeight -= items.elementAt(currentItem-1).x;
		resultingItems.add(currentItem-1);
	    }
	    currentItem--;
	    if (dp[currentItem][currentWeight] == 0) break;
	}
	return new Pair<Vector<Integer>, Integer>(resultingItems, dp[n][maxWeight]);
    }
}
