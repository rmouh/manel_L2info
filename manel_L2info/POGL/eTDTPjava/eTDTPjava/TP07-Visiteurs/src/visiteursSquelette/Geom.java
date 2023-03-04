package visiteursSquelette;

public class Geom {

    public static void main(String[] args) {
        Line e = new Line(0, 0, 300, 400, "red");
        Operation o = new Print();
        o.operate(e);
        apply(o, e);
    }

    /**
     * Applies an operation to an element.
     *
     * @param o The operation to be applied
     * @param e The element to which the operation is applied
     */
    public static void apply(Operation o, Element e) {
        o.operate(e);
    }
}

/**
 * Template class for geometric elements
 */
abstract class Element  {
    /**
     * Each element has a unique id, defined using a global counter.
     */
    private static int counter = 0;
    protected final int id;

    /**
     * Each element also has a colour.
     */
    protected String colour;

    public Element(String c) {
        this.id = counter++;
        this.colour = c;
    }

}


/**
 * A line segment is a particular kind of element
 */
class Line extends Element {

    /**
     * Coordinates of the two ends of the segment
     */
    protected int x1, y1;
    protected int x2, y2;

    public Line(int x1, int y1, int x2, int y2, String c) {
        super(c);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

}

/**
 * A circle is a particular kind of element
 */
class Circle extends Element {
    /**
     * Coordinates of the center, and radius
     */
    protected int x, y;
    protected int r;

    public Circle(int x, int y, int r, String c) {
        super(c);
        this.x = x;
        this.y = y;
        this.r = r;
    }



}


/**
 * Template class for the operations
 */
abstract class Operation  {

    /**
     * Operation on an element whose kind is not statically known
     */
    abstract public void operate(Element e);

    /**
     * Operations specific to lines and circle elements
     */
    public void operate(Line l) {
    }

    public void operate(Circle c) {
    }

}

/**
 * Operation that changes the colour of an element.
 */
class ChangeColour extends Operation {

    /**
     * The new colour
     */
    private String colour;

    public ChangeColour(String c) {
        colour = c;
    }

    /**
     * Change the colour of an element. Same code for line or circle
     *
     * @param e The element whose colour has to be assigned
     */
    @Override
    public void operate(Element e) {
        e.colour = colour;
    }
}


/**
 * Operation that prints information about an element.
 */
class Print extends Operation {
    /**
     * For an element whose kind is statically known: print the kind in SVG format
     */
    public void operate(Line l) {
        System.out.println("<line x1=\"" + l.x1 + "\" x2=\"" + l.x2 +
                "\" y1=\"" + l.y1 + "\" y2=\"" + l.y2 +
                "\" stroke=\"" + l.colour + "\"/>");
    }

    public void operate(Circle c) {
        System.out.println("<circle cx=\"" + c.x + "\" cy=\"" + c.y +
                "\" r=\"" + c.r +
                "\" stroke=\"" + c.colour + "\"/>");
    }

    /**
     * For an element whose kind is not statically known signal an error
     */
    public void operate(Element e) {
        System.out.println("error: element 's kind is not statically known");
    }

}

interface Visitor {
    public void visitLine(Line l);

    public void visitCircle(Circle c);
}

interface Visitable {
    public void accept(Visitor v);
}