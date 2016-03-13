import java.util.Stack;


public class Board {
	private int N;
	private char[] Board;
	private int BlankI;
	private int BlankJ;
	private int manhattan = -1;
	private int hamming = -1;
	private static int dx[] = {0,0,1,-1},
			dy[] = {1,-1,0,0};
	public Board(int[][] blocks){
		N = blocks.length;
		Board = new char[N*N];
		for (int i = 0; i<N; i++)
			for (int j = 0; j<N; j++) {
				Board[i*N+j] = (char)blocks[i][j];
				if (Board[i*N+j] == 0) {
					BlankI = i;
					BlankJ = j;
				}
			}

	}  
	private Board(char[] blocks,int N) {
		this.N = N;
		Board = new char[N*N];
		for (int i = 0; i<N; i++)
			for (int j = 0; j<N; j++) {
				Board[i*N+j] = blocks[i*N+j];
				if (Board[i*N+j] == 0) {
					BlankI = i;
					BlankJ = j;
				}
			}
	}
	public int dimension(){
		return N;
	}
	public int hamming()  {
		if (hamming == -1) {
			int s = 0;
			for (int i = 0; i<N; i++)
				for (int j=0; j<N; j++) 			
					if (Board[i*N+j] != 0 && Board[i*N+j] != i*N+j+1) s++;
			hamming = s;
		}
		return hamming;
	}
	public int manhattan()  {
		if (manhattan == -1) {
			int s = 0;
			for (int i = 0; i<N; i++)
				for (int j = 0;j<N; j++) {
					int sJ = (Board[i*N+j]-1)%N;
					int sI = (Board[i*N+j]-1)/N;
					if (Board[i*N+j] != 0) 
						s += Math.abs(sI - i) + Math.abs(sJ - j);
				}
			manhattan = s;
		}
		return manhattan;

	}
	public boolean isGoal(){
		return (manhattan() == 0);
	}
	public Board twin() {
		Board twin = new Board(Board,N);
		if (twin.Board[0] == 0 || twin.Board[1] == 0) exch(twin.Board,N,N+1);
		else exch(twin.Board,0,1);

		return twin;
	}  

	private void exch(char[] matrix, int i, int j) {
		char tmp = matrix[i];
		matrix[i] = matrix[j]; 
		matrix[j] = tmp;
	}
	public boolean equals(Object y) {
		if (y == null) return false;
		if (y == this) return true;
		if (y.getClass() != this.getClass()) return false;
		Board Copy = (Board) y;
		if (this.dimension() != Copy.dimension()) return false;
		for (int i = 0; i<N; i++)
			for (int j = 0;j<N; j++) 
				if (Board[i*N+j] != Copy.Board[i*N+j]) return false;
		return true;
	}
	public Iterable<Board> neighbors(){
		Stack <Board> st = new Stack<Board>();
		for (int i = 0; i<4; i++) {
			int xx = BlankI + dx[i],
					yy = BlankJ + dy[i];
			if (xx < 0 || yy < 0 || xx > N - 1 || yy > N - 1) continue;

			exch(Board,BlankI*N+BlankJ,xx*N + yy);
			st.push(new Board(Board,N));
			exch(Board,BlankI*N+BlankJ,xx*N + yy);
		}
		return st;
	}
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", (int)Board[i*N + j]));
			}
			s.append("\n");
		}
		return s.toString();
	}
}
