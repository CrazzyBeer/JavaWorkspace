import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Test {
	public static void main(String args[]) throws FileNotFoundException {
		Scanner in = new Scanner(new File("puzzle20.txt"));
		int N = in.nextInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.nextInt();
        Board initial = new Board(blocks);
        System.out.println(initial.manhattan());
        for (Board d : initial.neighbors()) {
        	System.out.println(d.toString());
        }
        in.close();
	}
}
