public class Particles {
	
	
	public static void main(String args[]) {
		int N = Integer.parseInt(args[0]);
		Ball[] balls = new Ball[N];
		StdDraw.setScale(0, 500);
		StdDraw.line(50, 0, 50, 500);
		for (int i = 0; i<N; i++) {
			balls[i] = new Ball();
		}
		while (true) {
			StdDraw.clear();
			for (int i = 0; i<N; i++) {
				balls[i].move();
				balls[i].draw();
			}
			StdDraw.show(20);
			
		}
		
	}
	
}
