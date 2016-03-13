
import java.io.FileNotFoundException;
import java.util.Arrays;


public class Fast {
	public static void main(String[] args) throws FileNotFoundException {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		/* !!!!!!!!!!!!!!!!!!!  StdDraw.setPenRadius(0.01); */
		In input = new In(args[0]);
		int n = input.readInt();
		Point[] arr = new Point[n];
		Point[] temp = new Point[n];
		for (int i = 0; i < n; i++) {
			arr[i] = new Point(input.readInt(),input.readInt());
			arr[i].draw();
			temp[i] = arr[i];
		}
		Arrays.sort(arr);
		for (int i = 0; i < n; i++) {
			   Point p = arr[i];
	           Arrays.sort(temp, p.SLOPE_ORDER);
	           
	           int start = 1;          
	           
	           do {
	               int count = 1;
	               double Slope = p.slopeTo(temp[start]);
	               int stop = start+1;
	               while (stop < n && p.slopeTo(temp[stop]) == Slope) {
	                   count++; 
	                   stop++; 
	               }
	               
	               if (count >= 3) {
	                   Arrays.sort(temp, start, stop);
	                   if (p.compareTo(temp[start]) < 0) {
	                	   StdOut.print(temp[0]);       
	                   		for (int l = start; l < stop; l++)
	                   			StdOut.print(" -> "+temp[l]);

	                   StdOut.println();
	                   temp[0].drawTo(temp[stop-1]);
	                   }
	               }
	               start = stop;
	               
	               
	           }
	           while (start < n);
	           
	       }
		
		
						
	}
}
