import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
	private Board start;
	private Node Solution = null;
	private boolean solved = false;
	private boolean isTwinSolvable = false;
	private int moves = 0;

	private class Node implements Comparable<Node> {
		public Board b;
		public int moves;
		public Node prev;
		public Node(Board b,int m, Node prev) {
			this.b = b;
			this.moves = m;
			this.prev = prev;
		}
		public int compareTo(Node that) {
			int thisP = this.moves + this.b.manhattan();
			int thatP = that.moves + that.b.manhattan();
			int thisH = this.moves + this.b.hamming();
			int thatH = that.moves + that.b.hamming();
			if (thisP < thatP) return -1;
			else if (thisP > thatP) return 1;
			else if (thisH < thatH) return -1;
			else if (thisH > thatH) return 1;
			else return 0;
		}
	}

	public Solver(Board initial){
		if (initial == null) throw new java.lang.NullPointerException();
		start = initial;
		MinPQ <Node> q1 = new MinPQ<Node>();
		MinPQ <Node> q2 = new MinPQ<Node>();
		q1.insert(new Node(start,0,null));
		q2.insert(new Node(start.twin(),0,null));

		while (!solved) {
			Node n1 = q1.delMin();
			if (n1.b.isGoal()) {
				Solution = n1;
				isTwinSolvable = false;
				moves = n1.moves;
				solved = true;
				return;
			}
			Iterable<Board> st1 = n1.b.neighbors();
			for (Board d : st1) {
				if (n1.prev == null || !d.equals(n1.prev.b) && !solved) q1.insert(new Node(d,n1.moves + 1,n1));
			}

			Node n2 = q2.delMin();
			if (n2.b.isGoal()) {
				Solution = n2;
				isTwinSolvable = true;
				moves = -1;
				solved = true;
				return;
			}
			Iterable<Board> st2 = n2.b.neighbors();
			for (Board d : st2) {
				if (n2.prev == null || !d.equals(n2.prev.b) && !solved) q2.insert(new Node(d,n2.moves + 1,n2));
			}

		}



	}    // find a solution to the initial board (using the A* algorithm)
	public boolean isSolvable(){
		return !isTwinSolvable;
	}
	public int moves(){
		return moves;
	}
	// min number of moves to solve initial board; -1 if unsolvable
	public Iterable<Board> solution(){
		if (!isSolvable()) return null;
		Stack <Board> st = new Stack<Board>();
		Node it = Solution;
		while (it != null) {
			st.push(it.b);
			it = it.prev;
		}
		return st;
	}      // sequence of boards in a shortest solution; null if unsolvable
	public static void main(String[] args){
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	} // solve a slider puzzle (given below)
}
