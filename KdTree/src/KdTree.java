import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;


public class KdTree {

	private static class Node {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node left;        // the left/bottom subtree
		private Node right;        // the right/top subtree
		
		public Node(Point2D p, RectHV rect) {
			this.p = p;
			this.rect = rect;
			left = null;
			right = null;
		}
		
	}

	private int size;
	private Node root;
	
	public KdTree(){
		size = 0;
		root = null;
	}
	public boolean isEmpty(){
		return size == 0;
	}
	public int size(){
		return size;
	}
	private void checker(Object o) {
		if (o == null) throw new java.lang.NullPointerException();
	}
	
	public void insert(Point2D p) {
        checker(p);
        if ( !contains(p) ) 
        	root = insert(root, p, 1, 0, 0, 1, 1);
    }
	private Node insert(Node x, Point2D p , int level, double xmin, double ymin, double xmax, double ymax ) {
        if (x == null) {
        	size ++;
        	return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        Point2D g = x.p;
        double 	px = p.x(),
				py = p.y(),
				gx = g.x(),
				gy = g.y();
        if (level % 2 == 1) {
        	if (px < gx ) x.left = insert(x.left, p, level + 1, xmin, ymin, gx, ymax );
            else if (px >= gx ) x.right = insert(x.right, p, level + 1, gx, ymin , xmax, ymax );
        } else {
        	if (py < gy ) x.left = insert(x.left, p, level + 1, xmin , ymin, xmax, gy );
            else if (py >= gy ) x.right = insert(x.right, p, level + 1, xmin, gy, xmax, ymax );
        }
        

        return x;
    }

	public boolean contains(Point2D p) {
		checker(p);
		return contains(root, p, 1);
	}
	private boolean contains(Node n, Point2D p, int level ) {
		if (n == null) return false;
		if (n.p.equals(p)) return true;
		Point2D g = n.p;
		double 	px = p.x(),
				py = p.y(),
				gx = g.x(),
				gy = g.y();
		
		if (level % 2 == 1) {
        	if (px < gx ) return contains(n.left, p, level + 1);
            else if (px >= gx ) return contains(n.right, p, level + 1);
        } else {
        	if (py < gy ) return contains(n.left, p, level + 1);
            else if (py >= gy ) return contains(n.right, p, level + 1);
        }
		return false;
	}
	
	public void draw() {
		draw(root, 1);
	}
	private void draw(Node n, int level) {
		if ( n == null) return;
		
		if (level % 2 == 1) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		StdDraw.point(n.p.x(), n.p.y());
		
		draw(n.left, level + 1);
		draw(n.right, level + 1);
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		checker(rect);
		
		ArrayList <Point2D> points = new ArrayList <Point2D> ();
		range(root, rect, points);
		return points;
	}
	private void range(Node x, RectHV rect, ArrayList <Point2D> set) {
		if (x == null) return;
		
		if (rect.intersects(x.rect)) {
			if (rect.contains(x.p)) set.add(x.p);
			range(x.left, rect, set);
			range(x.right, rect, set);
		}
		
	}
	
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        return nearest(root, p, root.p, true);
    }
    private Point2D nearest(Node x, Point2D p, Point2D mp, boolean vert) {
        Point2D min = mp;

        if (x == null)    return min;
        if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(min))
            min = x.p;

        // choose the side that contains the query point first
        if (vert) {
            if (x.p.x() < p.x()) {
                min = nearest(x.right, p, min, !vert);
                if (x.left != null
                        && (min.distanceSquaredTo(p)
                            > x.left.rect.distanceSquaredTo(p)))
                    min = nearest(x.left, p, min, !vert);
            } else {
                min = nearest(x.left, p, min, !vert);
                if (x.right != null
                        && (min.distanceSquaredTo(p)
                         > x.right.rect.distanceSquaredTo(p)))
                    min = nearest(x.right, p, min, !vert);
            }
        } else {
            if (x.p.y() < p.y()) {
                min = nearest(x.right, p, min, !vert);
                if (x.left != null
                        && (min.distanceSquaredTo(p)
                            > x.left.rect.distanceSquaredTo(p)))
                    min = nearest(x.left, p, min, !vert);
            } else {
                min = nearest(x.left, p, min, !vert);
                if (x.right != null
                        && (min.distanceSquaredTo(p)
                            > x.right.rect.distanceSquaredTo(p)))
                    min = nearest(x.right, p, min, !vert);
            }
        }
        return min;
    }

    // a nearest neighbor in the set to p: null if set is empty

}
