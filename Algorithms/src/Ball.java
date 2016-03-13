import java.awt.Color;

public class Ball {
		private double x;
		private double y;
		private double mass;
		private double vX;
		private double vY;
		private double radius;
		private Color color;
		public Ball(double x, double y, double mass, double vX, double vY, double radius, Color color) {
			this.x = x;
			this.y = y;
			this.mass = mass;
			this.vX = vX;
			this.vY = vY;
			this.radius = radius;
			this.color = color;
		}
		
		public Ball() {
			this.x = StdRandom.uniform(460) + 20;
			this.y = StdRandom.uniform(460) + 20;
			this.mass = StdRandom.uniform()*10;
			this.vX = StdRandom.uniform()*10 - 5;
			this.vY = StdRandom.uniform()*10 - 5;
			this.radius = 5;
			this.color = new Color(StdRandom.uniform(255),StdRandom.uniform(255),StdRandom.uniform(255));
		}
		
		
		public void move() {
			if (x + vX <= 1 + radius || x + vX + radius>= 499) 
				vX = -vX;
			if (y + vY <= 1 + radius || y + vY + radius>= 499) 
				vY = -vY;
				
			x += vX;
			y += vY;
		}
		public void move(double v) {
			this.x += v;
			this.y += v;
		}
	
		
		public void draw() {
			StdDraw.setPenColor(color);
			StdDraw.filledCircle(x, y, radius);
		}
		
		public double getX() {
			return x;
		}
		public void setX(double x) {
			this.x = x;
		}
		public double getY() {
			return y;
		}
		public void setY(double y) {
			this.y = y;
		}
		public double getMass() {
			return mass;
		}
		public void setMass(double mass) {
			this.mass = mass;
		}
		public double getvX() {
			return vX;
		}
		public void setvX(double vX) {
			this.vX = vX;
		}
		public double getvY() {
			return vY;
		}
		public void setvY(double vY) {
			this.vY = vY;
		}
		public double getRadius() {
			return radius;
		}
		public void setRadius(double radius) {
			this.radius = radius;
		}

	}