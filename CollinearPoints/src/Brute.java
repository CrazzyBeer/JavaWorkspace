
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Brute {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(args[0]));
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		/* !!!!!!!!!!!!!!!!!!!  StdDraw.setPenRadius(0.01); */
		int n = sc.nextInt();
		Point[] arr = new Point[n];
		
		for (int i = 0; i < n; i++) {
			arr[i] = new Point(sc.nextInt(),sc.nextInt());
			arr[i].draw();
		}
		Arrays.sort(arr);
		for (int i = 0; i < n-3; i++) 
			for (int j = i+1; j < n-2; j++)
				for (int k = j+1; k < n-1; k++)
					for (int l = k+1; l < n; l++) {
						double s1 = arr[i].slopeTo(arr[j]);
						double s2 = arr[i].slopeTo(arr[k]);
						double s3 = arr[i].slopeTo(arr[l]);
						
						if (s1 == s2 && s2 == s3) {
							StdOut.println(arr[i].toString()+" -> "+
										   arr[j].toString()+" -> "+
										   arr[k].toString()+" -> "+
										   arr[l].toString());
							arr[i].drawTo(arr[l]);
						}
					}
		sc.close();
						
	}
}
