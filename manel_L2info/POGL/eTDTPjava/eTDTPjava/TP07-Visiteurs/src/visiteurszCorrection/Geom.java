package visiteurszCorrection;

import java.util.ArrayList;

public class Geom {

    public static void main(String[] args) {
        Line e = new Line(0, 0, 300, 400, "red");
        Operation o = new Print();
        //o.operate(e);
        Operation o2 = new ChangeColour("red");
        apply(o, e);

        e.accept(o2);
        e.accept(o);
        e.accept(new Translate(50, 100));
        e.accept(o);
        Circle c = new Circle(0, 0, 200, "green");
        c.accept(o);
        c.accept(new Zoom(0.5));
        c.accept(o);
        Container g = new Container("blue");
        Container g2 = new Container("yellow");
        g.addElement(g2);
        g2.addElement(new Circle(0, 0, 200, "green"));
        g.addElement(e);
        g.addElement(c);
        g.accept(o);
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
abstract class Element implements Visitable {
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

    public abstract void print();

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

    @Override
    public void print() {
        System.out.println("<line x1=\"" +
                x1 + "\" x2=\"" +  x2 +
                "\" y1=\"" +  y1 + "\" y2=\"" +  y2 +
                "\" stroke=\"" +  colour + "\"/>");
    }

    @Override
    public void accept(Visitor v) {
        v.visitLine(this);
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

    @Override
    public void print() {
        System.out.println("<circle cx=\"" + x + "\" cy=\"" + y +
                "\" r=\"" + r +
                "\" stroke=\"" + colour + "\"/>");
    }
    public void accept(Visitor v) {
        v.visitCircle(this);
    }
}


/**
 * Template class for the operations
 */
abstract class Operation implements Visitor {

    /**
     * Operation on an element whose kind is not statically known
     */
    abstract public void operate(Element e);

    /**
     * Operations specific to lines and circle elements
     */
    public void operate(Line l) {}
    public void operate(Circle c) {}
    public void visitContainerIn(Container ct){};
    public void visitContainerOut(Container ct){};

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
     * Change the colour of an element.
     * @param e The element whose colour has to be assigned
     */
    @Override
    public void operate(Element e) {
        e.colour = colour;
    }
    public void visitLine(Line l) { operate(l) ;;  }
    public void visitCircle(Circle c) { operate(c) ;  }
}


/**
 * Operation that prints information about an element.
 */
class Print extends Operation {

    /**
     * For an element whose kind is not statically known: print "Element"
     */
/*    public void operate(Element e) {
        System.out.println("Element " + e.id + " " + e.colour);
    }*/
    public void operate(Element e) { e.print(); }

    /**
     * For an element whose kind is statically known: print the kind
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

   /* public void operate(Element e) {
        if (e instanceof Line) operateLine((Line) e);
        else operateCircle((Circle) e);
    }*/

    public void operateCircle(Circle c) {operate(c);}

    public void operateLine(Line l) {operate(l);}

    @Override
    public void visitLine(Line l) {operateLine(l);}

    @Override
    public void visitCircle(Circle c)  {operateCircle(c);}

    public void visitContainerIn(Container ct) {
        System.out.println("<g fill=\"" + ct.colour + "\">");
    }
    public void visitContainerOut(Container ct) {
        System.out.println("</g>");
    }
}


class Translate extends Operation {
    private int dx, dy;

    public Translate(int dx, int dy) {
        this.dx = dx; this.dy = dy;
    }

    public void operate(Element e) {  }

    public void visitLine(Line l) {
        l.x1 += dx; l.y1 += dy;
        l.x2 += dx; l.y2 += dy;
    }
    public void visitCircle(Circle c) {
        c.x += dx; c.y += dy;
    }
}
class Rotate extends Operation {
    private int dx, dy,angle;

    public Rotate(int dx, int dy,int a) {
        this.dx = dx; this.dy = dy; angle=a;
    }

    public void operate(Element e) {  }

    public void visitLine(Line l) {
        l.x1 += dx; l.y1 += dy;
        l.x2 += dx; l.y2 += dy;
    }
    public void visitCircle(Circle c) {
        c.x += dx; c.y += dy;
    }
}
class Zoom extends Operation {
    private double z;
    public Zoom(double f) {z=f;}
    public void operate(Element e) {    }

    public void visitLine(Line l) {
        l.x1 *= z; l.y1 *= z;
        l.x2 *= z; l.y2 *= z;
    }
    public void visitCircle(Circle c) {
        c.x *= z; c.y *= z;
        c.r *= z;
    }
}

interface Visitor {
    public void visitLine(Line l);
    public void visitCircle(Circle c);
    void visitContainerIn(Container ct);
    void visitContainerOut(Container ct);
}

interface Visitable {
    public void accept(Visitor v);
}


class Container extends Element {
    private ArrayList<Element> elements;

    public Container(String c) {
        super(c);
        this.elements = new ArrayList<Element>();
    }
    public void addElement(Element e) {
        elements.add(e);
    }

    @Override
    public void print() { }
    @Override
    public void accept(Visitor v)   {
        v.visitContainerIn(this);
        for(Element e : elements) { e.accept(v); }
        v.visitContainerOut(this);
    }

}