public class Line {
    private final Point pt1, pt2;

    /* constructors */
//    public Line(){
//        this.pt1 = new Point();     /* although seems pointless as it will be line with 0 length */
//        this.pt2 = new Point();
//    }
    public Line(Point pt1, Point pt2){
        this.pt1 = new Point(pt1);
        this.pt2 = new Point(pt2);
    }
    public Line(Line ln){
        this.pt1 = ln.pt1;
        this.pt2 = ln.pt2;
    }

    public void move(int x, int y) {
        this.pt1.move(x, y);
        this.pt2.move(x, y);
    }

    /* Point getters */
    public Point getPt1() { return this.pt1; }
    public Point getPt2() { return this.pt2; }

    @Override
    public String toString() {
        return "{" + this.pt1.toString() + ", " + this.pt2.toString() + "}";
    }
}
