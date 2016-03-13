import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;


public class PointSET {
	private int size;
	private SET <Point2D> set;
	public         PointSET(){
		size = 0;
		set = new SET <Point2D>();
	}
	public boolean isEmpty(){
		return set.isEmpty();
	}
	public int size(){
		return size;
	}
	private void checker(Object o) {
		if (o == null) throw new java.lang.NullPointerException();
	}
	public void insert(Point2D p) {
		checker(p);
		
		if (!contains(p)) {
			set.add(p);
			size++;
		}
	}
	public boolean contains(Point2D p) {
		checker(p);
		
		return set.contains(p);
	}
	public void draw() {
		for (Point2D key : set) {
			key.draw();
		}
	}
	public Iterable<Point2D> range(RectHV rect) {
		checker(rect);
		
		ArrayList <Point2D> points = new ArrayList <Point2D> ();
		for (Point2D key : set) {
			if (rect.contains(key)) points.add(key);
		}
		return points;
	}
	public Point2D nearest(Point2D p) {
		if (isEmpty()) return null;
		Point2D champ = new Point2D(0,0);
		double distance = -1;
		for (Point2D key : set) {
			double dist = p.distanceSquaredTo(key);
			if (distance == -1) {
				champ = key;
				distance = dist;
			} else if (dist < distance) {
				distance = dist;
				champ = key;
			}
		}
		return champ;	
	}
}
