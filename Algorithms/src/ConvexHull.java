import java.awt.Color;
import java.util.ArrayList;


public class ConvexHull {
	public static class Point {
		public double x;
		public double y;
		public Point(double x, double y) {
			this.x = x; 
			this.y = y;
		}
		public Point() {
			x = StdRandom.uniform(500)+10;
			y = StdRandom.uniform(500)+10;
		}
		public void drawTo(Point that,Color color) {
			StdDraw.setPenColor(color);
			StdDraw.line(this.x, this.y, that.x, that.y);
		}
		public void draw(Color color) {
			StdDraw.setPenColor(color);
			StdDraw.filledCircle(x, y, 3);
		}
	}
	
	static int N = 100;
	static Point[] arr;
	
	public static double ccw(Point a, Point b, Point c) {
		return (b.x-a.x)*(c.y-a.y)-(b.y-a.y)*(c.x-a.x);
	}
	
	public static void main(String[] args) {
		StdDraw.setScale(0, 550);
		if (args.length > 0) N = Integer.parseInt(args[0]);
			
		arr = new Point[N];
			
		for (int i = 0; i<N; i++) {
			arr[i] = new Point();
			arr[i].draw(Color.black);
		}
			
		int m = 0;
		for (int i = 0; i<N; i++) {
			if (arr[i].y < arr[m].y) m = i;
			else if (arr[i].y == arr[m].y && arr[i].x < arr[m].x) m = i;
		}
		
		int current = m;
		ArrayList <Point> res = new ArrayList<>();

		do {
			res.add(arr[current]);
			int next = current%(N-1) + 1;
			for (int i = 0; i<N; i++) {
				StdDraw.clear();
				for (int j = 0; j<N; j++) arr[j].draw(Color.BLACK);
				
				arr[current].drawTo(arr[next], Color.BLACK);
				arr[next].drawTo(arr[i], Color.GREEN);

				
				for (int j = 0; j<=res.size() - 2; j++) res.get(j).drawTo(res.get(j+1), Color.RED);
				

				
				if (ccw(arr[current], arr[next], arr[i] ) < 0 ) next = i;
				StdDraw.show(50);
			}
			current = next;
		}	while (current != m);
		
		res.get(res.size()-1).drawTo(res.get(0),Color.RED);
		StdDraw.show();
		
	}
}
