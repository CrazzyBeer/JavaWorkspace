
import java.util.Comparator;


public class Point implements Comparable<Point> {
   public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
   private final int x;                              // x coordinate
   private final int y;                              // y coordinate
   public Point(int x, int y) {
	   //SLOPE_ORDER = new Comparator<Point>();
	   this.x = x;
	   this.y = y;
   }
   
   
   public   void draw()  {
	   StdDraw.point(x, y);
   }
   public   void drawTo(Point that)  {
	   StdDraw.line(this.x, this.y, that.x, that.y);
   }
   public String toString()  {
	   return "(" + x + ", " + y + ")";
   }

   public    int compareTo(Point that) {
	   if (this.y < that.y) return -1;
	   else if (this.y > that.y) return +1;
	   else if (this.x < that.x) return -1;
	   else if (this.x > that.x) return +1;
	   else return 0;
   }
   public double slopeTo(Point that) {
	   int dx = that.x - this.x;
	   int dy = that.y - this.y;
	   if (dx == 0 && dy == 0) return Double.NEGATIVE_INFINITY;
	   else if (dx == 0) return Double.POSITIVE_INFINITY;
	   else if (dy == 0) return 0;
	   else return (double) dy/dx;
	   
   }
   private class SlopeOrder implements Comparator<Point> {
	   public int compare(Point arg0, Point arg1) {
			double slope1 = Point.this.slopeTo(arg0);
			double slope2 = Point.this.slopeTo(arg1);
			if (slope1 < slope2) return -1;
			else if (slope1 > slope2) return +1;
			else return 0;
		}
   }

}
